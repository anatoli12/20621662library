package bg.tuvarna.api.operations.administrator.addbookitems;

import bg.tuvarna.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddBookItemsInput implements ProcessorInput {
    @NotBlank(message = "ISBN must not be blank")
    @Length(min=14, max=14)
    private String isbn;
    @Positive
    private Integer quantity;
}
