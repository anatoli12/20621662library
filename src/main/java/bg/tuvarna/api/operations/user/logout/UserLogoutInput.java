package bg.tuvarna.api.operations.user.logout;

import bg.tuvarna.api.base.ProcessorInput;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLogoutInput implements ProcessorInput {
    private String etc;
}
