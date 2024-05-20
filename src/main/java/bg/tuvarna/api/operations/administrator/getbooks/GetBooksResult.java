package bg.tuvarna.api.operations.administrator.getbooks;

import bg.tuvarna.api.base.ProcessorResult;
import bg.tuvarna.api.operations.util.BookDTO;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetBooksResult implements ProcessorResult {
    private List<BookDTO> bookDTOList;
}
