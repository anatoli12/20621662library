package bg.tuvarna.api.operations.operator.getbookitemsforreader;

import bg.tuvarna.api.base.ProcessorInput;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetBookItemsForReaderInput implements ProcessorInput {
    private String email;
}
