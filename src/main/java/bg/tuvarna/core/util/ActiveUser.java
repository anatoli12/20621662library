package bg.tuvarna.core.util;

import bg.tuvarna.api.UserAuthority;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@Getter
@Setter
public class ActiveUser {
    private Optional<UUID> userId;
    private Optional<UserAuthority> userAuthority;
}
