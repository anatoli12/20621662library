package bg.tuvarna.api.operations.administrator.removearchivedbooks;

import bg.tuvarna.api.base.ProcessorInput;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveArchivedBookItemsInput implements ProcessorInput {
    private String placeHolder;
}
