package bg.tuvarna.core.service;

import bg.tuvarna.api.UserAuthority;
import bg.tuvarna.api.exception.IncorrectInputException;
import bg.tuvarna.api.exception.UserExistsException;
import bg.tuvarna.api.operations.administrator.createoperator.CreateOperator;
import bg.tuvarna.api.operations.administrator.createoperator.CreateOperatorInput;
import bg.tuvarna.api.operations.administrator.createoperator.CreateOperatorResult;
import bg.tuvarna.persistence.entity.User;
import bg.tuvarna.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateOperatorService implements CreateOperator {

    private final UserRepository userRepository;

    @Override
    public CreateOperatorResult process(CreateOperatorInput input) {
        Optional<User> existingUser = userRepository.findByEmail(input.getEmail());
        if (existingUser.isPresent()) {
            throw new UserExistsException("User with this email exists.");
        }
        if (!input.getPassword().equals(input.getConfirmPassword())) {
            throw new IncorrectInputException("Passwords don't match.");
        }
        var user = User.builder()
                .email(input.getEmail())
                .userAuthority(UserAuthority.OPERATOR)
                .password(input.getPassword())
                .build();
        userRepository.save(user);
        return CreateOperatorResult.builder()
                .operatorId(user.getId())
                .build();
    }
}
