package bg.tuvarna.frontend.controller;

import bg.tuvarna.api.operations.user.login.UserLoginInput;
import bg.tuvarna.api.operations.user.login.UserLoginResult;
import bg.tuvarna.api.operations.user.logout.UserLogoutInput;
import bg.tuvarna.api.operations.user.logout.UserLogoutResult;
import bg.tuvarna.core.service.UserLogoutService;
import bg.tuvarna.frontend.utils.SceneChanger;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

@Component
@Slf4j
public class AdminController {

    @FXML
    private Button addBookItemsButton;

    @FXML
    private ListView<?> bookList;

    @FXML
    private Button createOperatorButton;

    @FXML
    private Button logoutButton;

    @FXML
    private ListView<?> operatorList;

    @FXML
    private Button registerBookButton;

    @FXML
    private Button removeArchivedBookItemsButton;

    @FXML
    private Button removeDamagedBookItemsButton;

    @FXML
    private Button removeOperatorButton;

    @Autowired
    private UserLogoutService userLogoutService;
    @Autowired
    private SceneChanger sceneChanger;

    @Value("${fxml.paths.loginForm}")
    private String loginFormPath;
    @Value("${fxml.paths.createOperatorForm}")
    private String createOperatorFormPath;

    @FXML
    void createOperator() {
        sceneChanger.changeScene((Stage) logoutButton.getScene().getWindow(), createOperatorFormPath);
    }

    @FXML
    void logout(){
        try {
            userLogoutService.process(new UserLogoutInput());

            sceneChanger.changeScene((Stage) logoutButton.getScene().getWindow(), loginFormPath);
            log.info("User logged out successfully");
        } catch (Exception e){
//            log.error(e.printStackTrace());
            e.printStackTrace();
        }
    }

    void initialize(){

    }

}