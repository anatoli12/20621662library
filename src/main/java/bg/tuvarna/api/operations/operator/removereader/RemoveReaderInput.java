package bg.tuvarna.api.operations.operator.removereader;

import bg.tuvarna.api.base.ProcessorInput;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveReaderInput implements ProcessorInput {
    private String email;
}
