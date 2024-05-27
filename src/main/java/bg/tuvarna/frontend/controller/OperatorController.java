package bg.tuvarna.frontend.controller;

import bg.tuvarna.api.BookStatus;
import bg.tuvarna.api.operations.operator.changebookstatus.ChangeBookStatus;
import bg.tuvarna.api.operations.operator.changebookstatus.ChangeBookStatusInput;
import bg.tuvarna.api.operations.operator.getreaders.GetReaders;
import bg.tuvarna.api.operations.operator.getreaders.GetReadersInput;
import bg.tuvarna.api.operations.operator.lendbookitem.LendBookItem;
import bg.tuvarna.api.operations.operator.returnbook.ReturnBook;
import bg.tuvarna.api.operations.operator.returnbook.ReturnBookInput;
import bg.tuvarna.api.operations.operator.removebook.RemoveBook;
import bg.tuvarna.api.operations.operator.removebook.RemoveBookInput;
import bg.tuvarna.api.operations.operator.lendbookitem.LendBookItemInput;
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
import bg.tuvarna.persistence.entity.BookItem;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    private Button returnBookButton;
    @FXML
    private Button removeBookButton;
    @FXML
    private Button changeBookStatusButton;
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
    @Autowired
    private LendBookItem lendBookItem;
    @Autowired
    private ReturnBook ReturnBook;
    @Autowired
    private ChangeBookStatus changeBookStatus;
    @Autowired
    private RemoveBook RemoveBook;

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

    @FXML
    void lendBook() {
        ReaderDTO selectedReader = readersTableView.getSelectionModel().getSelectedItem();
        BookDTO selectedBook = booksTableView.getSelectionModel().getSelectedItem();

        if (selectedReader == null || selectedBook == null) {
            showAlert("Selection Error", "Please select a reader and a book.");
            return;
        }

        List<String> choices = Arrays.asList("Reading Room", "Rent Out");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Reading Room", choices);
        dialog.setTitle("Lend Book");
        dialog.setHeaderText("Choose lending option");
        dialog.setContentText("Lend the book in the reading room or rent it out:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            BookStatus bookStatus = result.get().equals("Reading Room") ? BookStatus.IN_READING_ROOM : BookStatus.RENTED_OUT;

            try {
                lendBookItem.process(
                        LendBookItemInput.builder()
                                .isbn(selectedBook.getIsbn())
                                .readerEmail(selectedReader.getEmail())
                                .bookStatus(bookStatus)
                                .build()
                );


                selectedBook.setQuantity(selectedBook.getQuantity() - 1);
                booksTableView.refresh();

                showAlert("Success", "Book has been successfully lent.");
            } catch (Exception e) {
                showAlert("Error", "Failed to lend the book: " + e.getMessage());
            }
        }
    }
    @FXML
    void returnBook() {
        ReaderDTO selectedReader = readersTableView.getSelectionModel().getSelectedItem();
        BookDTO selectedBook = booksTableView.getSelectionModel().getSelectedItem();

        if (selectedReader == null || selectedBook == null) {
            showAlert("Selection Error", "Please select a reader and a book.");
            return;
        }

        try {
            ReturnBook.process(
                    ReturnBookInput.builder()
                            .isbn(selectedBook.getIsbn())
                            .readerEmail(selectedReader.getEmail())
                            .build()
            );

            // Update the quantity in the BookDTO object and refresh the table view
            selectedBook.setQuantity(selectedBook.getQuantity() + 1);
            booksTableView.refresh();

            showAlert("Success", "Book has been successfully returned.");
        } catch (Exception e) {
            showAlert("Error", "Failed to return the book: " + e.getMessage());
        }
    }
    @FXML
    void removeBook() {
        BookDTO selectedBook = booksTableView.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            showAlert("Selection Error", "Please select a book to remove.");
            return;
        }

        try {
            RemoveBook.process(
                    RemoveBookInput.builder()
                            .isbn(selectedBook.getIsbn())
                            .build()
            );

            // Refresh the book list
            initBooks();

            showAlert("Success", "Book has been successfully removed.");
        } catch (Exception e) {
            showAlert("Error", "Failed to remove the book: " + e.getMessage());
        }
    }
    @FXML
    void changeBookStatus() {
        BookDTO selectedBook = booksTableView.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            showAlert("Selection Error", "Please select a book to change its status.");
            return;
        }

        List<String> choices = Arrays.asList(
                BookStatus.AVAILABLE.name(),
                BookStatus.RENTED_OUT.name(),
                BookStatus.DAMAGED.name(),
                BookStatus.IN_READING_ROOM.name(),
                BookStatus.ARCHIVED.name()
        );

        ChoiceDialog<String> dialog = new ChoiceDialog<>(BookStatus.AVAILABLE.name(), choices);
        dialog.setTitle("Change Book Status");
        dialog.setHeaderText("Choose new status for the book");
        dialog.setContentText("Select a status:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            BookStatus newStatus = BookStatus.valueOf(result.get());

            try {
                changeBookStatus.process(
                        ChangeBookStatusInput.builder()
                                .isbn(selectedBook.getIsbn()) // Use the ISBN here
                                .bookStatus(newStatus)
                                .build()
                );

                // Update the status in the BookDTO object and refresh the table view


                showAlert("Success", "Book status has been successfully changed.");
            } catch (Exception e) {
                showAlert("Error", "Failed to change the book status: " + e.getMessage());
            }
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
