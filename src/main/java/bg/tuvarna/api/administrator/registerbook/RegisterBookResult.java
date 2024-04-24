package bg.tuvarna.api.administrator.registerbook;

import bg.tuvarna.api.base.ProcessorResult;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterBookResult implements ProcessorResult {
    private Boolean isRegistered;
}
