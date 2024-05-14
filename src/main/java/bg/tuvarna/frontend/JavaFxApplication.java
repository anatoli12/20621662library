package bg.tuvarna.frontend;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;

import java.io.IOException;


public class JavaFxApplication extends javafx.application.Application {


    private static Logger logger;

    private AppContext context;
    private Parent rootNode;

    @Override
    public void init() throws Exception {
        logger = Logger.getLogger(this.getClass().getName());
        PropertyConfigurator.configure(getClass().getResource("src/main/resources/log4j.properties"));
        AppContext.getInstance().setContext(SpringApplication.run(bg.tuvarna.frontend.SpringApplication.class));
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFxApplication.class.getResource("/fxmls/login.fxml"));
        fxmlLoader.setControllerFactory(AppContext.getInstance().getContext()::getBean);
        rootNode = fxmlLoader.load();
    }

    @Override
    public void stop() {
        logger.log(Level.INFO, "Ended application");
        Platform.exit();
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(new Scene(rootNode));
        stage.show();
        logger.log(Level.INFO, "Started application");
    }

    private static void handleUncaughtException(Thread t, Throwable e){
        // Global exception handling logic here
        System.err.println("Uncaught exception in thread " + t.getName() + ": " + e.getMessage());
        e.printStackTrace();
    }
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(JavaFxApplication::handleUncaughtException);
        launch();
    }
}