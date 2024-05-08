package bg.tuvarna.api.administrator.removebookitems;

import bg.tuvarna.api.base.ProcessorInput;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemoveBookItemsInput implements ProcessorInput {
    private Set<String> isbns;
}
