package bg.tuvarna.frontend.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class MainController {

    @FXML
    private TextField email_form;

    @FXML
    private Label email_label;

    @FXML
    private Button login_button;

    @FXML
    private Label main_title;

    @FXML
    private PasswordField password_form;

    @FXML
    private Label password_label;

    @FXML
    void login() {

    }
}
