package bg.tuvarna.api.operations.administrator.removedamagedbooks;

import bg.tuvarna.api.base.ProcessorInput;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveDamagedBookItemsInput implements ProcessorInput {
    private String placeHolder;
}
