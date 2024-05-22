package bg.tuvarna.core.service;

import bg.tuvarna.api.operations.user.getbooks.GetBooks;
import bg.tuvarna.api.operations.user.getbooks.GetBooksInput;
import bg.tuvarna.api.operations.user.getbooks.GetBooksResult;
import bg.tuvarna.api.operations.util.BookDTO;
import bg.tuvarna.persistence.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GetBooksService implements GetBooks {

    private final BookRepository bookRepository;

    @Override
    public GetBooksResult process(GetBooksInput input) {
        return GetBooksResult.builder()
                .bookDTOList(getBookDTOList())
                .build();
    }

    private List<BookDTO> getBookDTOList() {
        return bookRepository.findAll().stream()
                .map(book -> BookDTO.builder()
                        .title(book.getTitle())
                        .author(book.getAuthor().getName())
                        .isbn(book.getIsbn())
                        .quantity(book.getBookItems().size())
                        .build())
                .toList();
    }
}
