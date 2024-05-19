package bg.tuvarna.api.operations.operator.lendbookitem;

import bg.tuvarna.api.BookStatus;
import bg.tuvarna.api.base.ProcessorResult;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LendBookItemResult implements ProcessorResult {
    private BookStatus bookStatus;
}
