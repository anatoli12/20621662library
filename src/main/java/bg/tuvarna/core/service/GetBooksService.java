package bg.tuvarna.core.service;

import bg.tuvarna.api.operations.administrator.getbooks.GetBooks;
import bg.tuvarna.api.operations.administrator.getbooks.GetBooksInput;
import bg.tuvarna.api.operations.administrator.getbooks.GetBooksResult;
import bg.tuvarna.api.operations.util.BookDTO;
import bg.tuvarna.persistence.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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
                        .quantity(book.getBookItems().size())
                        .build())
                .collect(Collectors.toList());
    }
}