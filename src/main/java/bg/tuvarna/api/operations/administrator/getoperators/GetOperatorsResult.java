package bg.tuvarna.api.operations.administrator.getoperators;

import bg.tuvarna.api.base.ProcessorResult;
import bg.tuvarna.api.operations.util.OperatorDTO;
import lombok.*;

import java.util.List;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetOperatorsResult implements ProcessorResult {
    private List<OperatorDTO> operators;
}
