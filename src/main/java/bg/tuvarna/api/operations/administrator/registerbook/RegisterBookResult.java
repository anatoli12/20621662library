package bg.tuvarna.api.operations.administrator.registerbook;

import bg.tuvarna.api.base.ProcessorResult;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterBookResult implements ProcessorResult {
    private String bookId;
}
