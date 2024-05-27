package bg.tuvarna.frontend.controller;

import bg.tuvarna.api.operations.operator.getbookitemsforreader.GetBookItemsForReaderInput;
import bg.tuvarna.api.operations.operator.getbookitemsforreader.GetBookItemsForReaderResult;
import bg.tuvarna.api.operations.util.BookItemDTO;
import bg.tuvarna.core.service.GetBookItemsForReaderService;
import bg.tuvarna.frontend.utils.SceneChanger;
import javafx.fxml.FXML;
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
    private SceneChanger sceneChanger;
    @Value("${fxml.paths.operatorForm}")
    private String operatorFormPath;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("bookStatus"));

        // Load book items for the reader (example, replace with actual data loading logic)
        if (readerEmail != null && !readerEmail.isEmpty()) {
            loadBookItems();
        }
    }

    @FXML
    void returnBook() {
        // Implement the return book logic
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
    void goBack(){
        sceneChanger.changeScene((Stage) backButton.getScene().getWindow(), operatorFormPath);
    }

}
