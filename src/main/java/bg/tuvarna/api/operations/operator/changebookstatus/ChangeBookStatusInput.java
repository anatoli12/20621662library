package bg.tuvarna.api.operations.operator.changebookstatus;

import bg.tuvarna.api.BookStatus;
import bg.tuvarna.api.base.ProcessorInput;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeBookStatusInput implements ProcessorInput {
    private String bookItemId;
    private BookStatus bookStatus;
}
