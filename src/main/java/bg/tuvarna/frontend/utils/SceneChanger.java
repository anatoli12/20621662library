package bg.tuvarna.frontend.utils;

import bg.tuvarna.frontend.AppContext;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SceneChanger {

    public static void changeScene(Stage stage, String sceneName) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneChanger.class.getResource(sceneName));
        loader.setControllerFactory(AppContext.getInstance().getContext()::getBean);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
