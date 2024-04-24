package bg.tuvarna.api.user.login;

import bg.tuvarna.api.UserAuthority;
import bg.tuvarna.api.base.ProcessorResult;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResult implements ProcessorResult {
    private UUID userId;
    private UserAuthority authority;
}
