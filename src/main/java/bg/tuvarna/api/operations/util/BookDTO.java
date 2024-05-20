package bg.tuvarna.api.operations.util;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private String title;
    private String author;
    private Integer quantity;
}
