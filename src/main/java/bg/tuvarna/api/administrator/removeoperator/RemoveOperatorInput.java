package bg.tuvarna.api.administrator.removeoperator;

import bg.tuvarna.api.base.ProcessorInput;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveOperatorInput implements ProcessorInput {
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid email format")
    private String operatorEmail;
}
