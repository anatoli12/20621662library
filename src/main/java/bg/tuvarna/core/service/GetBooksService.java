package bg.tuvarna.core.service;

import bg.tuvarna.api.operations.user.getbooks.GetBooks;
import bg.tuvarna.api.operations.user.getbooks.GetBooksInput;
import bg.tuvarna.api.operations.user.getbooks.GetBooksResult;
import bg.tuvarna.api.operations.util.BookDTO;
import bg.tuvarna.persistence.entity.BookItem;
import bg.tuvarna.persistence.repository.BookItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GetBooksService implements GetBooks {

    private final BookItemRepository bookItemRepository;

    @Override
    public GetBooksResult process(GetBooksInput input) {
        List<BookDTO> bookDTOList = bookItemRepository.findAll().stream()
                .map(this::mapToBookDTO)
                .collect(Collectors.toList());

        return GetBooksResult.builder()
                .bookDTOList(bookDTOList)
                .build();
    }

    private BookDTO mapToBookDTO(BookItem bookItem) {
        return BookDTO.builder()
                .title(bookItem.getBook().getTitle())
                .author(bookItem.getBook().getAuthor().getName())
                .isbn(bookItem.getBook().getIsbn())
                .quantity(bookItem.getBook().getBookItems().size())
                .build();
    }
}
