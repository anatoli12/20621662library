package bg.tuvarna.api.administrator.createoperator;

import bg.tuvarna.api.base.ProcessorResult;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOperatorResult implements ProcessorResult {
    private UUID operatorId;
}
