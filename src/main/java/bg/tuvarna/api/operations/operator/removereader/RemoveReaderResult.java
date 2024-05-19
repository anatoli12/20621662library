package bg.tuvarna.api.operations.operator.removereader;

import bg.tuvarna.api.base.ProcessorResult;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveReaderResult implements ProcessorResult {
    private String name;
}
