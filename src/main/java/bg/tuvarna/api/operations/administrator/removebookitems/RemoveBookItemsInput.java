package bg.tuvarna.api.operations.administrator.removebookitems;

import bg.tuvarna.api.base.ProcessorInput;
import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveBookItemsInput implements ProcessorInput {
    private Set<String> isbns;
}
