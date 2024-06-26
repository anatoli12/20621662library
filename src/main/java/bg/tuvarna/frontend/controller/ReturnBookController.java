package bg.tuvarna.frontend.controller;

import bg.tuvarna.api.operations.operator.getbookitemsforreader.GetBookItemsForReaderInput;
import bg.tuvarna.api.operations.operator.getbookitemsforreader.GetBookItemsForReaderResult;
import bg.tuvarna.api.operations.operator.returnbook.ReturnBookItem;
import bg.tuvarna.api.operations.operator.returnbook.ReturnBookItemInput;
import bg.tuvarna.api.operations.util.BookItemDTO;
import bg.tuvarna.core.service.GetBookItemsForReaderService;
import bg.tuvarna.frontend.utils.SceneChanger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReturnBookController {

    @FXML
    private TableView<BookItemDTO> booksTable;

    @FXML
    private TableColumn<BookItemDTO, String> idColumn;

    @FXML
    private TableColumn<BookItemDTO, String> isbnColumn;

    @FXML
    private Button returnBookButton;

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<BookItemDTO, String> statusColumn;

    @FXML
    private TableColumn<BookItemDTO, String> titleColumn;

    @Setter
    private String readerEmail;

    @Autowired
    private GetBookItemsForReaderService getBookItemsForReader;
    @Autowired
    private ReturnBookItem returnBookItem;

    @Autowired
    private SceneChanger sceneChanger;
    @Value("${fxml.paths.operatorForm}")
    private String operatorFormPath;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("bookStatus"));

        if (readerEmail != null && !readerEmail.isEmpty()) {
            loadBookItems();
        }
    }

    @FXML
    void returnBook() {
        BookItemDTO selectedBookItem = booksTable.getSelectionModel().getSelectedItem();
        if (selectedBookItem != null) {
            returnBookItem.process(ReturnBookItemInput.builder()
                    .bookItemId(selectedBookItem.getId())
                    .build());
        } else {
            // Show an alert if no book is selected
            showAlert("No Selection", "No book selected", "Please select a book to return.");
        }
        initialize();
    }

    private void loadBookItems() {
        GetBookItemsForReaderResult result = getBookItemsForReader.process(
                GetBookItemsForReaderInput.builder()
                        .email(readerEmail)
                        .build()
        );

        List<BookItemDTO> bookItemDTOList = result.getBookItemDTOList();
        booksTable.getItems().clear();
        booksTable.getItems().addAll(bookItemDTOList);
    }

    @FXML
    void goBack() {
        sceneChanger.changeScene((Stage) backButton.getScene().getWindow(), operatorFormPath);
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
