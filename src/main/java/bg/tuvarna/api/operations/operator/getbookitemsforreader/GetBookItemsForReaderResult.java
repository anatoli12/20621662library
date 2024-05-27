package bg.tuvarna.api.operations.operator.getbookitemsforreader;

import bg.tuvarna.api.base.ProcessorResult;
import bg.tuvarna.api.operations.util.BookDTO;
import bg.tuvarna.api.operations.util.BookItemDTO;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetBookItemsForReaderResult implements ProcessorResult {
    private List<BookItemDTO> bookItemDTOList;
}
