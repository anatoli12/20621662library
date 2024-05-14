package bg.tuvarna.core.service;

import bg.tuvarna.api.UserAuthority;
import bg.tuvarna.api.exception.IncorrectInputException;
import bg.tuvarna.api.exception.UserExistsException;
import bg.tuvarna.api.operations.administrator.createoperator.CreateOperator;
import bg.tuvarna.api.operations.administrator.createoperator.CreateOperatorInput;
import bg.tuvarna.api.operations.administrator.createoperator.CreateOperatorResult;
import bg.tuvarna.api.operations.operator.createreader.CreateReaderInput;
import bg.tuvarna.persistence.entity.User;
import bg.tuvarna.persistence.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateOperatorService implements CreateOperator {

    private final UserRepository userRepository;
    private final Validator validator;
    @Override
    public CreateOperatorResult process(CreateOperatorInput input) {
        Set<ConstraintViolation<CreateOperatorInput>> violations = validator.validate(input);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        log.info("Processing CreateOperatorInput: {}", input);

        if (!input.getPassword().equals(input.getConfirmPassword())) {
            log.warn("Passwords do not match for email {}", input.getEmail());
            throw new IncorrectInputException("Passwords don't match.");
        }

        Optional<User> existingUser = userRepository.findByEmail(input.getEmail());
        if (existingUser.isPresent()) {
            log.warn("User with email {} already exists.", input.getEmail());
            throw new UserExistsException("User with this email exists.");
        }

        var user = User.builder()
                .email(input.getEmail())
                .userAuthority(UserAuthority.OPERATOR)
                .password(input.getPassword())
                .build();

        log.info("Saving new User: {}", user);
        userRepository.save(user);
        log.info("New User saved successfully with id {}", user.getId());

        return CreateOperatorResult.builder()
                .operatorId(user.getId())
                .build();
    }
}