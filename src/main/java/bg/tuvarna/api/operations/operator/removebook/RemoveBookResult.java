package bg.tuvarna.api.operations.operator.removebook;

import bg.tuvarna.api.base.ProcessorResult;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveBookResult implements ProcessorResult {
    private boolean isSuccessful;
}
