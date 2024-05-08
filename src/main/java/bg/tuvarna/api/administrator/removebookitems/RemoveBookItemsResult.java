package bg.tuvarna.api.administrator.removebookitems;

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
