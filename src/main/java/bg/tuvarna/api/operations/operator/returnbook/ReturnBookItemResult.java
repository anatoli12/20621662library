package bg.tuvarna.api.operations.operator.returnbook;

import bg.tuvarna.api.base.ProcessorResult;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnBookItemResult implements ProcessorResult {
    private boolean isSuccessful;
}
