package bg.tuvarna.api.operations.administrator.registerbook;

import bg.tuvarna.api.BookGenre;
import bg.tuvarna.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterBookInput implements ProcessorInput {
    @NotBlank(message = "You need to have a title!")
    private String title;
    @NotBlank(message = "ISBN must not be blank")
    @Length(min = 14, max = 14)
    private String isbn;
    @NotBlank(message = "Who wrote the book?")
    private String author;
    @NotNull
    private BookGenre bookGenre;
}
