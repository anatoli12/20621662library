package bg.tuvarna.api.operations.operator.removebook;

import bg.tuvarna.api.base.ProcessorInput;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveBookInput implements ProcessorInput {
    private String isbn;
}
