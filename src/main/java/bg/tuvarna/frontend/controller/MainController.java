package bg.tuvarna.frontend.controller;

import bg.tuvarna.api.operations.user.login.UserLoginInput;
import bg.tuvarna.api.operations.user.login.UserLoginResult;
import bg.tuvarna.core.service.UserLoginService;
import bg.tuvarna.frontend.utils.SceneChanger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MainController {

    @FXML
    private TextField emailForm;

    @FXML
    private Label emailLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Label mainTitle;

    @FXML
    private PasswordField passwordForm;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label userNotFoundLabel;

    @Autowired
    private UserLoginService userLoginService;

    @FXML
    void login() {

        UserLoginInput userLoginInput = UserLoginInput.builder()
                .email(emailForm.getText())
                .password(passwordForm.getText())
                .build();
        try {
            UserLoginResult result = userLoginService.process(userLoginInput);
            SceneChanger.changeScene((Stage) mainTitle.getScene().getWindow(), "${fxml.paths.loginForm}");
            log.info("User logged in successfully: {}", userLoginInput.getEmail());
        } catch (Exception e){
            userNotFoundLabel.setText(e.getMessage());
            log.error(e.getMessage());
        }
    }

}