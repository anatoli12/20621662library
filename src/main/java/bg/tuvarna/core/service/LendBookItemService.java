package bg.tuvarna.core.service;

import bg.tuvarna.api.BookStatus;
import bg.tuvarna.api.exception.BookNotFoundException;
import bg.tuvarna.api.exception.UserNotFoundException;
import bg.tuvarna.api.operations.operator.lendbookitem.LendBookItem;
import bg.tuvarna.api.operations.operator.lendbookitem.LendBookItemInput;
import bg.tuvarna.api.operations.operator.lendbookitem.LendBookItemResult;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LendBookItemService implements LendBookItem {

    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;
    private final BookItemRepository bookItemRepository;

    @Override
    @Transactional
    public LendBookItemResult process(LendBookItemInput input) {
        log.info("Processing lend book item for reader email: {} and book isbn: {}", input.getReaderEmail(), input.getIsbn());

        Reader reader = readerRepository.findByEmail(input.getReaderEmail())
                .orElseThrow(() -> {
                    log.warn("Reader with email {} not found.", input.getReaderEmail());
                    return new UserNotFoundException("User with email not found");
                });

        Book book = bookRepository.findBookByIsbn(input.getIsbn()).get();
        Optional<BookItem> bookItemToLend = bookItemRepository.findAllByBook(book)
                .stream()
                .filter(bookItem -> bookItem.getBookStatus() == BookStatus.AVAILABLE)
                .findFirst();

        if (bookItemToLend.isEmpty()) {
            throw new BookNotFoundException("No available book items found for this book.");
        }

        BookItem availableBookItem = bookItemToLend.get();

        reader.getBookItemList().add(availableBookItem);
        readerRepository.save(reader);

        availableBookItem.setBookStatus(input.getBookStatus());
        availableBookItem.setReader(reader);
        bookItemRepository.save(availableBookItem);
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        log.info("Book item with ID {} successfully lent to reader with email {}", availableBookItem.getId(), input.getReaderEmail());

        return LendBookItemResult.builder()
                .bookStatus(availableBookItem.getBookStatus())
                .build();
    }
}