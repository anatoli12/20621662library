package bg.tuvarna.api.operations.operator.changebookstatus;

import bg.tuvarna.api.BookStatus;
import bg.tuvarna.api.base.ProcessorInput;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangeBookStatusInput implements ProcessorInput {
    private String isbn;
    private BookStatus bookStatus;
}
