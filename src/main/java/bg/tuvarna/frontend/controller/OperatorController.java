package bg.tuvarna.frontend.controller;

import bg.tuvarna.api.operations.operator.getreaders.GetReaders;
import bg.tuvarna.api.operations.operator.getreaders.GetReadersInput;
import bg.tuvarna.api.operations.operator.removereader.RemoveReaderInput;
import bg.tuvarna.api.operations.user.getbooks.GetBooks;
import bg.tuvarna.api.operations.user.getbooks.GetBooksInput;
import bg.tuvarna.api.operations.operator.createreader.CreateReader;
import bg.tuvarna.api.operations.operator.removereader.RemoveReader;
import bg.tuvarna.api.operations.user.logout.UserLogout;
import bg.tuvarna.api.operations.user.logout.UserLogoutInput;
import bg.tuvarna.api.operations.util.BookDTO;
import bg.tuvarna.api.operations.util.ReaderDTO;
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
public class OperatorController {

    @FXML
    private TableColumn<?, ?> bookAuthorColumn;

    @FXML
    private TableColumn<?, ?> bookIsbnColumn;

    @FXML
    private TableColumn<?, ?> bookQuantityColumn;

    @FXML
    private TableColumn<?, ?> bookTitleColumn;

    @FXML
    private TableView<BookDTO> booksTableView;

    @FXML
    private Button createReaderButton;

    @FXML
    private Button deleteReaderButton;

    @FXML
    private Button logoutButton;
    @FXML
    private TableColumn<?, ?> readerEmailColumn;

    @FXML
    private TableColumn<?, ?> readerFirstNameColumn;

    @FXML
    private TableColumn<?, ?> readerLastNameColumn;

    @FXML
    private TableColumn<?, ?> readerPhoneNumberColumn;

    @FXML
    private TableColumn<?, ?> readerRatingColumn;

    @FXML
    private TableView<ReaderDTO> readersTableView;
    @Value("${fxml.paths.createReaderForm}")
    private String createReaderPath;
    @Value("${fxml.paths.loginForm}")
    private String loginFormPath;
    @Autowired
    private SceneChanger sceneChanger;

    @Autowired
    private CreateReader createReader;
    @Autowired
    private RemoveReader removeReader;
    @Autowired
    private GetBooks getBooks;
    @Autowired
    private UserLogout userLogout;
    @Autowired
    private GetReaders getReaders;

    @FXML
    void initialize(){

        initBooks();
        initReaders();
    }

    @FXML
    void createReader() {
        sceneChanger.changeScene((Stage) createReaderButton.getScene().getWindow(), createReaderPath);
    }

    @FXML
    void removeReader() {
        ReaderDTO selectedReader = readersTableView.getSelectionModel().getSelectedItem();
        if (selectedReader != null) {
            try {
                RemoveReaderInput input = RemoveReaderInput.builder()
                        .email(selectedReader.getEmail())
                        .build();
                removeReader.process(input);
                log.info("Reader with email {} deleted successfully", selectedReader.getEmail());

                initReaders();
            } catch (Exception e) {
                log.error("Error deleting reader: {}", e.getMessage());
            }
        } else {
            log.warn("No reader selected for deletion.");
        }
    }

    private void initBooks() {
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        bookQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        bookIsbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        ObservableList<BookDTO> bookDTOS = FXCollections.observableArrayList(
                getBooks.process(new GetBooksInput()).getBookDTOList()
        );
        booksTableView.setItems(bookDTOS);
    }

    public void logout() {
        try {
            userLogout.process(new UserLogoutInput());

            sceneChanger.changeScene((Stage) logoutButton.getScene().getWindow(), loginFormPath);
            log.info("User logged out successfully");
        } catch (Exception e) {
            log.error(e.getMessage());
//            e.printStackTrace();
        }
    }

    private void initReaders() {
        readerFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        readerLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        readerPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        readerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        readerRatingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        ObservableList<ReaderDTO> readerDTOS = FXCollections.observableArrayList(
                getReaders.process(new GetReadersInput()).getReaderDTOList()
        );
        readersTableView.setItems(readerDTOS);
    }

}
