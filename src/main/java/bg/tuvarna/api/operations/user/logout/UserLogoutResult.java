package bg.tuvarna.api.operations.user.logout;

import bg.tuvarna.api.base.ProcessorResult;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLogoutResult implements ProcessorResult {
    private boolean success;
}
