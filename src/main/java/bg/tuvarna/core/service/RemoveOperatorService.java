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
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RemoveOperatorService implements RemoveOperator {
    private final UserRepository userRepository;
    @Override
    public RemoveOperatorResult process(RemoveOperatorInput input) {
        Optional<User> operator = userRepository.findByEmail(input.getOperatorEmail());

        if(operator.isEmpty()){
            throw new UserNotFoundException("User with these credentials not found");
        }
        if(!operator.get().getUserAuthority().equals(UserAuthority.OPERATOR)){
            throw new UnauthorizedException("Insufficent privileges! (Don't delete yourself man, c'mon!)");
        }
        userRepository.delete(operator.get());

        return RemoveOperatorResult.builder()
                .msg("Operator with email ".concat(input.getOperatorEmail()).concat(" removed!"))
                .build();
    }
}
