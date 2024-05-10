package bg.tuvarna.core.service;

import bg.tuvarna.api.exception.IncorrectInputException;
import bg.tuvarna.api.exception.UserNotFoundException;
import bg.tuvarna.api.operations.user.login.UserLogin;
import bg.tuvarna.api.operations.user.login.UserLoginInput;
import bg.tuvarna.api.operations.user.login.UserLoginResult;
import bg.tuvarna.core.util.ActiveUser;
import bg.tuvarna.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserLoginService implements UserLogin {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final ActiveUser activeUser;

    @Override
    public UserLoginResult process(UserLoginInput input) {
        if (input.getEmail().isEmpty() || input.getPassword().isEmpty()) {
            throw new IncorrectInputException("Email or password cannot be empty");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
        var user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User does not exist."));

        activeUser.setUserId(Optional.ofNullable(user.getId()));

        return UserLoginResult.builder()
                .userId(activeUser.getUserId().get())
                .build();

    }
}
