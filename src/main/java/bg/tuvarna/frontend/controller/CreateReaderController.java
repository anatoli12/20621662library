package bg.tuvarna.frontend.controller;

import bg.tuvarna.api.operations.operator.createreader.CreateReader;
import bg.tuvarna.api.operations.operator.createreader.CreateReaderInput;
import bg.tuvarna.api.operations.operator.createreader.CreateReaderResult;
import bg.tuvarna.frontend.utils.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateReaderController {

    @FXML
    private Button backButton;

    @FXML
    private Button createReaderButton;

    @FXML
    private Text createReaderLabel;

    @FXML
    private TextField emailField;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField firstNameField;

    @FXML
    private Label firstNameLabel;

    @FXML
    private TextField lastNameField;

    @FXML
    private Label lastNameLabel;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private Label failedCreationLabel;

    @Value("${fxml.paths.operatorForm}")
    private String operatorViewPath;

    @Autowired
    private SceneChanger sceneChanger;

    @Autowired
    private CreateReader createReader;

    @FXML
    void createReader(ActionEvent event) {
        CreateReaderInput input = CreateReaderInput.builder()
                .firstName(firstNameField.getText())
                .lastName(lastNameField.getText())
                .phoneNumber(phoneNumberField.getText())
                .email(emailField.getText())
                .build();

        try {
            CreateReaderResult result = createReader.process(input);
            log.info("Reader created successfully: {}", result);
        } catch (Exception e) {
            log.error("Error creating reader: {}", e.getMessage());

            failedCreationLabel.setText("Failed to create reader: " + e.getMessage());
            return;
        }
        sceneChanger.changeScene((Stage) backButton.getScene().getWindow(), operatorViewPath);
    }

    @FXML
    void goBack(ActionEvent event) {
        sceneChanger.changeScene((Stage) backButton.getScene().getWindow(), operatorViewPath);
    }
}
