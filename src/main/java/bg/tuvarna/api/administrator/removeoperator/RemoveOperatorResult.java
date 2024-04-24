package bg.tuvarna.api.administrator.removeoperator;

import bg.tuvarna.api.base.ProcessorResult;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveOperatorResult implements ProcessorResult {
    private String msg;
}
