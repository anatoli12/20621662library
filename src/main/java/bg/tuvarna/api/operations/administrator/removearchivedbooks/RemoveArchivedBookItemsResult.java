package bg.tuvarna.api.operations.administrator.removearchivedbooks;

import bg.tuvarna.api.base.ProcessorResult;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveArchivedBookItemsResult implements ProcessorResult {
    private Boolean isSuccessful;
}
