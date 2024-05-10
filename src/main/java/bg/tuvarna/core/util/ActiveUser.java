package bg.tuvarna.core.util;

import bg.tuvarna.api.UserAuthority;
import lombok.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Getter
@Setter
public class ActiveUser {
    private Optional<UUID> userId;
}
