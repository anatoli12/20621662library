package bg.tuvarna.api.operations.administrator.addbookitems;

import bg.tuvarna.api.base.ProcessorResult;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddBookItemsResult implements ProcessorResult {
    private Boolean isSuccessful;
    private String message;
}
