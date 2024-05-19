package bg.tuvarna.api.operations.operator.lendbookitem;

import bg.tuvarna.api.BookStatus;
import bg.tuvarna.api.base.ProcessorInput;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LendBookItemInput implements ProcessorInput {
    private String readerEmail;
    private String bookItemId;
    private BookStatus bookStatus;
}
