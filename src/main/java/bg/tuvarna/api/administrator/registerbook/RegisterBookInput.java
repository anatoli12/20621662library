package bg.tuvarna.api.administrator.registerbook;

import bg.tuvarna.api.BookGenre;
import bg.tuvarna.api.base.ProcessorInput;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterBookInput implements ProcessorInput {
    private String title;
    private String isbn;
    private String author;
    private BookGenre bookGenre;
}
