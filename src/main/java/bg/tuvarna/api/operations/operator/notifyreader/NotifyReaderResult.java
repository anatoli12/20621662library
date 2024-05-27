package bg.tuvarna.api.operations.operator.notifyreader;

import bg.tuvarna.api.base.ProcessorResult;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotifyReaderResult implements ProcessorResult {
    private String recipient;
    private String body;
    private String filePath;
}
