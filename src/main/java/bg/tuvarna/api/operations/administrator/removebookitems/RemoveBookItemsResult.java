package bg.tuvarna.api.operations.administrator.removebookitems;

import bg.tuvarna.api.base.ProcessorResult;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveBookItemsResult implements ProcessorResult {
    private Boolean isSuccessful;
}
