package bg.tuvarna.frontend.utils;

import bg.tuvarna.frontend.AppContext;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SceneChanger {

    private final SpringFXMLLoader springFXMLLoader;

    public void changeScene(Stage stage, String fxmlFilePath) {
        try {
            Parent root = (Parent) springFXMLLoader.load(fxmlFilePath);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}