package bg.tuvarna.core.service;

import bg.tuvarna.api.exception.BookExistsException;
import bg.tuvarna.api.operations.administrator.registerbook.RegisterBook;
import bg.tuvarna.api.operations.administrator.registerbook.RegisterBookInput;
import bg.tuvarna.api.operations.administrator.registerbook.RegisterBookResult;
import bg.tuvarna.persistence.entity.Author;
import bg.tuvarna.persistence.entity.Book;
import bg.tuvarna.persistence.repository.AuthorRepository;
import bg.tuvarna.persistence.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterBookService implements RegisterBook {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
//TODO: INPUT VALIDATION
    @Override
    public RegisterBookResult process(RegisterBookInput input) {
        log.info("Processing RegisterBookInput: {}", input);

        if (bookRepository.findBookByIsbn(input.getIsbn()).isPresent()) {
            log.warn("Book with ISBN {} already exists.", input.getIsbn());
            throw new BookExistsException("Book with the provided ISBN already in database.");
        }

        Author author = getAuthor(input.getAuthor());
        log.info("Author found or created: {}", author);

        Book book = bookRepository.save(
                Book.builder()
                        .title(input.getTitle())
                        .isbn(input.getIsbn())
                        .author(author)
                        .bookGenre(input.getBookGenre())
                        .bookItems(new ArrayList<>())
                        .build());

        log.info("Book saved successfully: {}", book);

        return RegisterBookResult.builder()
                .bookId(String.valueOf(book.getId()))
                .build();
    }

    private Author getAuthor(String authorName) {
        log.info("Looking up author: {}", authorName);
        return authorRepository.findAuthorByName(authorName)
                .orElseGet(() -> {
                    log.info("Author not found, creating new author: {}", authorName);
                    Author newAuthor = Author.builder()
                            .name(authorName)
                            .books(new ArrayList<>())
                            .build();
                    Author savedAuthor = authorRepository.save(newAuthor);
                    log.info("New author created: {}", savedAuthor);
                    return savedAuthor;
                });
    }
}
