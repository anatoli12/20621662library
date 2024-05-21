package bg.tuvarna.core.service;

import bg.tuvarna.api.UserAuthority;
import bg.tuvarna.api.operations.administrator.getoperators.GetOperators;
import bg.tuvarna.api.operations.administrator.getoperators.GetOperatorsInput;
import bg.tuvarna.api.operations.administrator.getoperators.GetOperatorsResult;
import bg.tuvarna.api.operations.util.OperatorDTO;
import bg.tuvarna.persistence.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GetOperatorsService implements GetOperators {

    private final UserRepository userRepository;
    @Override
    public GetOperatorsResult process(GetOperatorsInput input) {
        return GetOperatorsResult.builder()
                .operators(getOperatorsFromUsers())
                .build();
    }

    private List<OperatorDTO> getOperatorsFromUsers(){
        return userRepository.findAllByUserAuthority(UserAuthority.OPERATOR).stream()
                .map(user -> OperatorDTO.builder()
                        .email(user.getEmail())
                        .id(String.valueOf(user.getId()))
                        .build())
                .collect(Collectors.toList());
    }
}
