package bg.tuvarna.api.operations.util;

import bg.tuvarna.api.BookStatus;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookItemDTO {
    private String id;
    private String title;
    private String isbn;
    private BookStatus bookStatus;
}
