package bg.tuvarna.api.operations.operator.changebookstatus;

import bg.tuvarna.api.BookStatus;
import bg.tuvarna.api.base.ProcessorResult;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeBookStatusResult implements ProcessorResult {
    private BookStatus bookStatus;
}
