package bg.tuvarna.frontend.utils;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

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