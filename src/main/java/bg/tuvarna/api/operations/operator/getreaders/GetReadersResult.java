package bg.tuvarna.api.operations.operator.getreaders;

import bg.tuvarna.api.base.ProcessorResult;
import bg.tuvarna.api.operations.util.ReaderDTO;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetReadersResult implements ProcessorResult {
    private List<ReaderDTO> readerDTOList;
}
