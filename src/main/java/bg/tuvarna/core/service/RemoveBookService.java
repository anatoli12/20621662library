package bg.tuvarna.core.service;

import bg.tuvarna.api.operations.operator.removebook.RemoveBook;
import bg.tuvarna.api.operations.operator.removebook.RemoveBookInput;
import bg.tuvarna.api.operations.operator.removebook.RemoveBookResult;
import bg.tuvarna.persistence.entity.Book;
import bg.tuvarna.persistence.repository.BookRepository;
import bg.tuvarna.persistence.repository.BookItemRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RemoveBookService implements RemoveBook {
    private static final Logger logger = LoggerFactory.getLogger(RemoveBookService.class);
    private final BookRepository bookRepository;
    private final BookItemRepository bookItemRepository;

    @Override
    @Transactional
    public RemoveBookResult process(RemoveBookInput input) {
        logger.info("Processing remove book for ISBN: {}", input.getIsbn());

        Optional<Book> bookOptional = bookRepository.findBookByIsbn(input.getIsbn());
        if (bookOptional.isEmpty()) {
            logger.error("Book with ISBN {} not found", input.getIsbn());
            throw new IllegalArgumentException("Book not found");
        }

        Book book = bookOptional.get();

        // Remove all book items associated with the book
        bookItemRepository.deleteAll(book.getBookItems());

        // Remove the book itself
        bookRepository.delete(book);

        logger.info("Book with ISBN {} removed successfully", input.getIsbn());

        return RemoveBookResult.builder()
                .isSuccessful(true)
                .build();
    }
}
