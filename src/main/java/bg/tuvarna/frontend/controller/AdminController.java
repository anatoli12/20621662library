package bg.tuvarna.frontend.controller;

import bg.tuvarna.api.operations.administrator.getbooks.GetBooks;
import bg.tuvarna.api.operations.administrator.getbooks.GetBooksInput;
import bg.tuvarna.api.operations.administrator.getoperators.GetOperators;
import bg.tuvarna.api.operations.administrator.getoperators.GetOperatorsInput;
import bg.tuvarna.api.operations.administrator.removeoperator.RemoveOperator;
import bg.tuvarna.api.operations.administrator.removeoperator.RemoveOperatorInput;
import bg.tuvarna.api.operations.user.logout.UserLogout;
import bg.tuvarna.api.operations.user.logout.UserLogoutInput;
import bg.tuvarna.api.operations.util.BookDTO;
import bg.tuvarna.api.operations.util.OperatorDTO;
import bg.tuvarna.frontend.utils.SceneChanger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class AdminController {

    @FXML
    private Button addBookItemsButton;

    @FXML
    private TableColumn<?, ?> bookAuthorColumn;
    @FXML

    private TableColumn<?, ?> bookQuantityColumn;

    @FXML
    private TableView bookTable;

    @FXML
    private TableColumn<?, ?> bookTitleColumn;

    @FXML
    private Button createOperatorButton;

    @FXML
    private Button logoutButton;

    @FXML
    private TableColumn<?, ?> operatorEmailColumn;
    @FXML
    private TableColumn<?, ?> operatorIdColumn;

    @FXML
    private TableView<OperatorDTO> operatorTable;

    @FXML
    private Button registerBookButton;

    @FXML
    private Button removeArchivedBookItemsButton;

    @FXML
    private Button removeDamagedBookItemsButton;

    @FXML
    private Button removeOperatorButton;

    @Autowired
    private UserLogout userLogout;
    @Autowired
    private GetOperators getOperators;
    @Autowired
    private SceneChanger sceneChanger;
    @Autowired
    private RemoveOperator removeOperator;
    @Autowired
    private GetBooks getBooks;

    @Value("${fxml.paths.loginForm}")
    private String loginFormPath;
    @Value("${fxml.paths.createOperatorForm}")
    private String createOperatorFormPath;
    @Value("${fxml.paths.registerBookForm}")
    private String registerBookFormPath;

    @FXML
    void createOperator() {
        sceneChanger.changeScene((Stage) logoutButton.getScene().getWindow(), createOperatorFormPath);
    }

    @FXML
    void registerBook() {
        sceneChanger.changeScene((Stage) registerBookButton.getScene().getWindow(), registerBookFormPath);
    }

    @FXML
    void logout() {
        try {
            userLogout.process(new UserLogoutInput());

            sceneChanger.changeScene((Stage) logoutButton.getScene().getWindow(), loginFormPath);
            log.info("User logged out successfully");
        } catch (Exception e) {
            log.error(e.getMessage());
//            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        initOperators();
        initBooks();
    }

    @FXML
    void removeOperator() {
        // Get the selected item from the TableView
        Optional.ofNullable(operatorTable.getSelectionModel().getSelectedItem()).ifPresentOrElse(
                selectedOperator -> {
                    // Show a confirmation dialog
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Confirmation");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Are you sure you want to delete the operator " + selectedOperator.getEmail() + "?");

                    Optional<ButtonType> result = confirmationAlert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        // Create input for RemoveOperator process
                        RemoveOperatorInput input = RemoveOperatorInput.builder()
                                .operatorEmail(selectedOperator.getEmail())
                                .build();

                        // Process the removal
                        removeOperator.process(input);

                        // Optionally, you might want to remove the item from the table view
                        operatorTable.getItems().remove(selectedOperator);
                    }
                },
                () -> {
                    // Handle the case when no item is selected
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Please select an operator to remove.");
                    alert.showAndWait();
                }
        );
    }

    private void initOperators() {

        operatorEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        operatorIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));


        ObservableList<OperatorDTO> operators = FXCollections.observableArrayList(
                getOperators.process(new GetOperatorsInput()).getOperators()
        );
        operatorTable.setItems(operators);
    }

    private void initBooks() {
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        bookQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        ObservableList<BookDTO> bookDTOS = FXCollections.observableArrayList(
                getBooks.process(new GetBooksInput()).getBookDTOList()
        );
        bookTable.setItems(bookDTOS);
    }

}