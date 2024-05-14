package bg.tuvarna.frontend;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ConfigurableApplicationContext;

public class AppContext {
    private static AppContext appContext = null;

    @Getter
    @Setter
    private ConfigurableApplicationContext context;

    public AppContext() {
    }

    public static AppContext getInstance() {
        if (appContext == null) {
            appContext = new AppContext();
        }
        return appContext;
    }
}