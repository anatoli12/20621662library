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
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class RegisterBookService implements RegisterBook {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public RegisterBookResult process(RegisterBookInput input) {
        if (bookRepository.findBookByIsbn(input.getIsbn()).isPresent()) {
            throw new BookExistsException("Book with the provided ISBN already in database.");
        }

        Author author = getAuthor(input.getAuthor());
        Book book = bookRepository.save(
                Book.builder()
                .title(input.getTitle())
                .isbn(input.getIsbn())
                .author(author)
                .bookGenre(input.getBookGenre())
                .bookItems(new ArrayList<>())
                .build());

        return RegisterBookResult.builder()
                .bookId(String.valueOf(book.getId()))
                .build();

    }

    private Author getAuthor(String authorName) {
        return authorRepository.findAuthorByName(authorName)
                .orElseGet(() -> {
                            Author newAuthor = Author.builder()
                                    .name(authorName)
                                    .books(new ArrayList<>())
                                    .build();

                            return authorRepository.save(newAuthor);
                        }
                );
    }
}
