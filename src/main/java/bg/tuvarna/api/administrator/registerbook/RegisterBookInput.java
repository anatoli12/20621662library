package bg.tuvarna.api.administrator.registerbook;

import bg.tuvarna.api.BookGenre;
import bg.tuvarna.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterBookInput implements ProcessorInput {
    @NotBlank
    private String title;
    @NotBlank(message = "ISBN must not be blank")
    @Length(min=14, max=14)
    private String isbn;
    @NotBlank
    private String author;
    private BookGenre bookGenre;
}
