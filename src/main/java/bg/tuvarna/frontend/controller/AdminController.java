package bg.tuvarna.frontend.controller;

import bg.tuvarna.api.operations.administrator.getbooks.GetBooks;
import bg.tuvarna.api.operations.administrator.getbooks.GetBooksInput;
import bg.tuvarna.api.operations.administrator.getoperators.GetOperators;
import bg.tuvarna.api.operations.administrator.getoperators.GetOperatorsInput;
import bg.tuvarna.api.operations.user.logout.UserLogout;
import bg.tuvarna.api.operations.user.logout.UserLogoutInput;
import bg.tuvarna.api.operations.util.BookDTO;
import bg.tuvarna.api.operations.util.OperatorDTO;
import bg.tuvarna.frontend.utils.SceneChanger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
    private TableView operatorTable;

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
    private GetBooks getBooks;

    @Value("${fxml.paths.loginForm}")
    private String loginFormPath;
    @Value("${fxml.paths.createOperatorForm}")
    private String createOperatorFormPath;

    @FXML
    void createOperator() {
        sceneChanger.changeScene((Stage) logoutButton.getScene().getWindow(), createOperatorFormPath);
    }

    @FXML
    void logout() {
        try {
            userLogout.process(new UserLogoutInput());

            sceneChanger.changeScene((Stage) logoutButton.getScene().getWindow(), loginFormPath);
            log.info("User logged out successfully");
        } catch (Exception e) {
//            log.error(e.printStackTrace());
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        initOperators();
    }

    private void initOperators(){

        operatorEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        operatorIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));


        ObservableList<OperatorDTO> operators = FXCollections.observableArrayList(
                getOperators.process(new GetOperatorsInput()).getOperators()
        );
        operatorTable.setItems(operators);
    }

    private void initBooks(){
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        bookQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        ObservableList<BookDTO> bookDTOS = FXCollections.observableArrayList(
                getBooks.process(new GetBooksInput()).getBookDTOList()
        );
        bookTable.setItems(bookDTOS);
    }

}