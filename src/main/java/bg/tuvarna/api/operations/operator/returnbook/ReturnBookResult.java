package bg.tuvarna.api.operations.operator.returnbook;

import bg.tuvarna.api.BookStatus;
import bg.tuvarna.api.base.ProcessorResult;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnBookResult implements ProcessorResult {
    private BookStatus bookStatus;
}

