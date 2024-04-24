package bg.tuvarna.api.user.login;

import bg.tuvarna.api.base.ProcessorInput;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginInput implements ProcessorInput {
    private String email;
    private String password;
}
