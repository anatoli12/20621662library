package bg.tuvarna.frontend.controller;

import bg.tuvarna.api.operations.administrator.createoperator.CreateOperator;
import bg.tuvarna.api.operations.administrator.createoperator.CreateOperatorInput;
import bg.tuvarna.api.operations.administrator.createoperator.CreateOperatorResult;
import bg.tuvarna.frontend.utils.SceneChanger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CreateOperatorController {

    @FXML
    private Button backButton;

    @FXML
    private PasswordField confirmPasswordForm;

    @FXML
    private Label confirmPasswordLabel;

    @FXML
    private Button createOperatorButton;

    @FXML
    private Text createOperatorLabel;

    @FXML
    private TextField emailForm;

    @FXML
    private Label emailLabel;

    @FXML
    private PasswordField passwordForm;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label failedCreationLabel;

    @Autowired
    private SceneChanger sceneChanger;
    @Value("${fxml.paths.adminForm}")
    private String adminViewPath;
    @Autowired
    private CreateOperator createOperator;

    @FXML
    void goBack() {
        sceneChanger.changeScene((Stage) backButton.getScene().getWindow(), adminViewPath);

    }

    @FXML
    void register() {
        CreateOperatorInput input = CreateOperatorInput.builder()
                .email(emailForm.getText())
                .password(passwordForm.getText())
                .confirmPassword(confirmPasswordForm.getText())
                .build();

        try {
            CreateOperatorResult result = createOperator.process(input);
            log.info("Operator created successfully: {}", result);
        } catch (Exception e) {
            log.error("Error creating operator: {}", e.getMessage());

            failedCreationLabel.setText("Failed to create operator: " + e.getMessage());
        }
        sceneChanger.changeScene((Stage) backButton.getScene().getWindow(), adminViewPath);
    }

}


