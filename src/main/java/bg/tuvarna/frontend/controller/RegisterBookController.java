package bg.tuvarna.frontend.controller;

import bg.tuvarna.api.BookGenre;
import bg.tuvarna.api.operations.administrator.registerbook.RegisterBook;
import bg.tuvarna.api.operations.administrator.registerbook.RegisterBookInput;
import bg.tuvarna.api.operations.administrator.registerbook.RegisterBookResult;
import bg.tuvarna.frontend.utils.SceneChanger;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

@Component
@Slf4j
public class RegisterBookController {

    @FXML
    private TextField authorForm;

    @FXML
    private Label authorLabel;

    @FXML
    private Button backButton;

    @FXML
    private Label failedRegistrationLabel;

    @FXML
    private ComboBox<BookGenre> genreForm;

    @FXML
    private Label genreLabel;

    @FXML
    private TextField isbnForm;

    @FXML
    private Label isbnLabel;

    @FXML
    private Button registerBookButton;

    @FXML
    private Text registerBookLabel;

    @FXML
    private TextField titleForm;

    @FXML
    private Label titleLabel;

    @Value("${fxml.paths.adminForm}")
    private String adminViewPath;
    @Autowired
    private SceneChanger sceneChanger;
    @Autowired
    private RegisterBook registerBook;

    @FXML
    void initialize() {
        genreForm.getItems().setAll(BookGenre.values());
    }
    @FXML
    void goBack() {
        sceneChanger.changeScene((Stage) backButton.getScene().getWindow(), adminViewPath);
    }

    @FXML
    void registerBook() {
        RegisterBookInput input = RegisterBookInput.builder()
                .title(titleForm.getText())
                .isbn(isbnForm.getText())
                .author(authorForm.getText())
                .bookGenre(genreForm.getValue())
                .build();

        try {
            RegisterBookResult result = registerBook.process(input);
            log.info("Book registered successfully: {}", result);
        } catch (Exception e) {
            log.error("Error registering book: {}", e.getMessage());
            failedRegistrationLabel.setText("Failed to register book: " + e.getMessage());
        }
        finally {
            goBack();
        }
    }

}