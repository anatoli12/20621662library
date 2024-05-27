package bg.tuvarna.api.operations.operator.notifyreader;

import bg.tuvarna.api.base.ProcessorInput;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotifyReaderInput implements ProcessorInput {
    private String email;
}
