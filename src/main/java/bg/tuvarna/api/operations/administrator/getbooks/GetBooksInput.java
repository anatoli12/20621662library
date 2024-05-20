package bg.tuvarna.api.operations.administrator.getbooks;

import bg.tuvarna.api.base.ProcessorInput;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetBooksInput implements ProcessorInput {
    //future pagination implementation
    private Integer page;
    private Integer elementsOnPage;
}
