package bg.tuvarna.api.operations.administrator.removedamagedbooks;

import bg.tuvarna.api.base.ProcessorResult;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveDamagedBookItemsResult implements ProcessorResult {
    private Integer removedCount;
}
