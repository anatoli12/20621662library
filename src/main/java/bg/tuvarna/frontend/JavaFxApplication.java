package bg.tuvarna.frontend;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

import java.io.IOException;

@Slf4j
public class JavaFxApplication extends javafx.application.Application {
    private AppContext context;
    private Parent rootNode;

    @Override
    public void init() throws Exception {
        AppContext.getInstance().setContext(SpringApplication.run(bg.tuvarna.frontend.SpringApplication.class));
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFxApplication.class.getResource("/javafxml/main-page.fxml"));
        fxmlLoader.setControllerFactory(AppContext.getInstance().getContext()::getBean);
        rootNode = fxmlLoader.load();
    }

    @Override
    public void stop() {
        log.info("Ended application");
        Platform.exit();
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(new Scene(rootNode));
        stage.show();
        log.info("Started application");
    }

    public static void main(String[] args) {
        launch();
    }
}