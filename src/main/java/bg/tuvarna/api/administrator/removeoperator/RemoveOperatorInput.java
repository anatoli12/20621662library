package bg.tuvarna.api.administrator.removeoperator;

import bg.tuvarna.api.base.ProcessorInput;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveOperatorInput implements ProcessorInput {
    private String operatorEmail;
}
