package bg.tuvarna.api.administrator.createoperator;

import bg.tuvarna.api.base.ProcessorInput;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOperatorInput implements ProcessorInput {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
}
