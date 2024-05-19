package bg.tuvarna.core.service;

import bg.tuvarna.api.exception.IncorrectInputException;
import bg.tuvarna.api.exception.UserNotFoundException;
import bg.tuvarna.api.operations.user.login.UserLogin;
import bg.tuvarna.api.operations.user.login.UserLoginInput;
import bg.tuvarna.api.operations.user.login.UserLoginResult;
import bg.tuvarna.core.util.ActiveUser;
import bg.tuvarna.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserLoginService implements UserLogin {
    private final UserRepository userRepository;
//    private final AuthenticationManager authenticationManager;
    private final ActiveUser activeUser;

    @Override
    public UserLoginResult process(UserLoginInput input) {
        log.info("Processing UserLoginInput: {}", input);

        if (input.getEmail().isEmpty() || input.getPassword().isEmpty()) {
            log.warn("Email or password is empty.");
            throw new IncorrectInputException("Email or password cannot be empty");
        }

        var user = userRepository.findByEmailAndPassword(input.getEmail(), input.getPassword())
                .orElseThrow(() -> {
                    log.warn("Incorrect username or password", input.getEmail());
                    return new UserNotFoundException("Incorrect username or password");
                });

        log.info("User authenticated successfully: {}", user);

        activeUser.setUserId(Optional.ofNullable(user.getId()));

        log.info("Setting active user ID: {}", activeUser.getUserId().orElse(null));

        return UserLoginResult.builder()
                .userId(activeUser.getUserId().get())
                .build();
    }
}