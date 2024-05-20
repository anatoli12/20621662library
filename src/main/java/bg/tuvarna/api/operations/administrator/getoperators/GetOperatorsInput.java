package bg.tuvarna.api.operations.administrator.getoperators;

import bg.tuvarna.api.base.ProcessorInput;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetOperatorsInput implements ProcessorInput {
    //future pagination implementation
    private Integer page;
    private Integer elementsOnPage;
}
