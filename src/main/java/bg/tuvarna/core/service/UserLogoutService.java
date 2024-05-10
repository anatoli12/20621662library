package bg.tuvarna.core.service;

import bg.tuvarna.api.operations.user.logout.UserLogout;
import bg.tuvarna.api.operations.user.logout.UserLogoutInput;
import bg.tuvarna.api.operations.user.logout.UserLogoutResult;
import bg.tuvarna.core.util.ActiveUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLogoutService implements UserLogout {

    private final ActiveUser activeUser;
    @Override
    public UserLogoutResult process(UserLogoutInput input) {
        activeUser.setUserId(Optional.empty());
        return new UserLogoutResult(true);
    }
}
