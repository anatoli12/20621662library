package bg.tuvarna.api.administrator.addbookitems;

import bg.tuvarna.api.base.ProcessorInput;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddBookItemsInput implements ProcessorInput {
    private String isbn;
    private Integer quantity;
}
