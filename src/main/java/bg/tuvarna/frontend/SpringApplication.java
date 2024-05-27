package bg.tuvarna.frontend;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "bg.tuvarna")
@EntityScan(basePackages = "bg.tuvarna.persistence.entity")
@EnableJpaRepositories(basePackages = "bg.tuvarna.persistence.repository")
public class SpringApplication {
    public static void main(String[] args) {
        Application.launch(JavaFxApplication.class, args);
    }
}