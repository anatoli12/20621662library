package bg.tuvarna.core.service;

import bg.tuvarna.api.BookStatus;
import bg.tuvarna.api.exception.BookNotFoundException;
import bg.tuvarna.api.exception.UserNotFoundException;
import bg.tuvarna.api.operations.operator.returnbook.ReturnBook;
import bg.tuvarna.api.operations.operator.returnbook.ReturnBookInput;
import bg.tuvarna.api.operations.operator.returnbook.ReturnBookResult;
import bg.tuvarna.persistence.entity.Book;
import bg.tuvarna.persistence.entity.BookItem;
import bg.tuvarna.persistence.entity.Reader;
import bg.tuvarna.persistence.repository.BookItemRepository;
import bg.tuvarna.persistence.repository.BookRepository;
import bg.tuvarna.persistence.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReturnBookService implements ReturnBook {

    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;
    private final BookItemRepository bookItemRepository;

    @Override
    @Transactional
    public ReturnBookResult process(ReturnBookInput input) {
        log.info("Processing return book item for reader email: {} and book ISBN: {}", input.getReaderEmail(), input.getIsbn());

        Reader reader = readerRepository.findByEmail(input.getReaderEmail())
                .orElseThrow(() -> {
                    log.warn("Reader with email {} not found.", input.getReaderEmail());
                    return new UserNotFoundException("User with email not found");
                });

        Book book = bookRepository.findBookByIsbn(input.getIsbn())
                .orElseThrow(() -> {
                    log.warn("Book with ISBN {} not found.", input.getIsbn());
                    return new BookNotFoundException("Book not found");
                });

        Optional<BookItem> bookItemOptional = bookItemRepository.findAllByBook(book)
                .stream()
                .filter(bookItem -> bookItem.getReader() != null && bookItem.getReader().equals(reader))
                .findFirst();

        if (bookItemOptional.isEmpty()) {
            log.warn("No book item for ISBN {} found for reader with email {}", input.getIsbn(), input.getReaderEmail());
            throw new BookNotFoundException("Reader does not have this book item.");
        }

        BookItem bookItem = bookItemOptional.get();

        reader.getBookItemList().remove(bookItem);
        readerRepository.save(reader);

        bookItem.setBookStatus(BookStatus.AVAILABLE);
        bookItem.setReader(null);
        book.setQuantity(book.getQuantity() + 1);
        bookItemRepository.save(bookItem);

        log.info("Book item with ISBN {} successfully returned by reader with email {}", input.getIsbn(), input.getReaderEmail());

        return ReturnBookResult.builder()
                .bookStatus(bookItem.getBookStatus())
                .build();
    }
}

