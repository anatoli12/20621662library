package bg.tuvarna.api.operations.util;

import lombok.*;
import bg.tuvarna.api.BookStatus;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private String title;
    private String author;
    private String isbn;
    private Integer quantity;
}
