package bg.tuvarna.core.service;

import bg.tuvarna.api.UserAuthority;
import bg.tuvarna.api.exception.UnauthorizedException;
import bg.tuvarna.api.exception.UserNotFoundException;
import bg.tuvarna.api.operations.administrator.removeoperator.RemoveOperator;
import bg.tuvarna.api.operations.administrator.removeoperator.RemoveOperatorInput;
import bg.tuvarna.api.operations.administrator.removeoperator.RemoveOperatorResult;
import bg.tuvarna.persistence.entity.User;
import bg.tuvarna.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RemoveOperatorService implements RemoveOperator {
    private final UserRepository userRepository;

    @Override
    public RemoveOperatorResult process(RemoveOperatorInput input) {
        log.info("Processing RemoveOperatorInput: {}", input);

        Optional<User> operator = userRepository.findByEmail(input.getOperatorEmail());

        if (operator.isEmpty()) {
            log.warn("User with email {} not found.", input.getOperatorEmail());
            throw new UserNotFoundException("User with these credentials not found");
        }

        if (!operator.get().getUserAuthority().equals(UserAuthority.OPERATOR)) {
            log.warn("User with email {} does not have OPERATOR authority.", input.getOperatorEmail());
            throw new UnauthorizedException("Insufficient privileges! (Don't delete yourself man, c'mon!)");
        }

        userRepository.delete(operator.get());
        log.info("Operator with email {} removed.", input.getOperatorEmail());

        return RemoveOperatorResult.builder()
                .msg("Operator with email ".concat(input.getOperatorEmail()).concat(" removed!"))
                .build();
    }
}