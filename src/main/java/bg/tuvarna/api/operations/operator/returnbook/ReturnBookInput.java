package bg.tuvarna.api.operations.operator.returnbook;

import bg.tuvarna.api.base.ProcessorInput;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnBookInput implements ProcessorInput {
    private String readerEmail;
    private String isbn;
}

