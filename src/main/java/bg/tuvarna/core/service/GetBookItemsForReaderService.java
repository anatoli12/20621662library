package bg.tuvarna.core.service;

import bg.tuvarna.api.operations.operator.getbookitemsforreader.GetBookItemsForReader;
import bg.tuvarna.api.operations.operator.getbookitemsforreader.GetBookItemsForReaderInput;
import bg.tuvarna.api.operations.operator.getbookitemsforreader.GetBookItemsForReaderResult;
import bg.tuvarna.api.operations.util.BookItemDTO;
import bg.tuvarna.persistence.entity.BookItem;
import bg.tuvarna.persistence.entity.Reader;
import bg.tuvarna.persistence.repository.BookItemRepository;
import bg.tuvarna.persistence.repository.ReaderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class GetBookItemsForReaderService implements GetBookItemsForReader {
    private final BookItemRepository bookItemRepository;
    private final ReaderRepository readerRepository;

    @Override
    public GetBookItemsForReaderResult process(GetBookItemsForReaderInput input) {
        Reader reader = readerRepository.findByEmail(input.getEmail()).get();

        List<BookItem> readerBookItems = bookItemRepository.findAllByReader(reader);
        List<BookItemDTO> bookItemDTOList = readerBookItems.stream()
                .map(this::convertToDTO)
                .toList();

        return GetBookItemsForReaderResult.builder()
                .bookItemDTOList(bookItemDTOList)
                .build();
    }

    private BookItemDTO convertToDTO(BookItem bookItem) {
        return BookItemDTO.builder()
                .id(bookItem.getId().toString())
                .title(bookItem.getBook().getTitle())
                .isbn(bookItem.getBook().getIsbn())
                .bookStatus(bookItem.getBookStatus())
                .build();
    }
}
