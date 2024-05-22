package bg.tuvarna.core.service;

import bg.tuvarna.api.exception.BookNotFoundException;
import bg.tuvarna.api.exception.UserNotFoundException;
import bg.tuvarna.api.operations.operator.lendbookitem.LendBookItem;
import bg.tuvarna.api.operations.operator.lendbookitem.LendBookItemInput;
import bg.tuvarna.api.operations.operator.lendbookitem.LendBookItemResult;
import bg.tuvarna.persistence.entity.BookItem;
import bg.tuvarna.persistence.entity.Reader;
import bg.tuvarna.persistence.repository.BookItemRepository;
import bg.tuvarna.persistence.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LendBookItemService implements LendBookItem {

    private final ReaderRepository readerRepository;
    private final BookItemRepository bookItemRepository;

    @Override
    @Transactional
    public LendBookItemResult process(LendBookItemInput input) {
        log.info("Processing lend book item for reader email: {} and book item ID: {}", input.getReaderEmail(), input.getBookItemId());

        Reader reader = readerRepository.findByEmail(input.getReaderEmail())
                .orElseThrow(() -> {
                    log.warn("Reader with email {} not found.", input.getReaderEmail());
                    return new UserNotFoundException("User with email not found");
                });

        UUID bookItemId;
        try {
            bookItemId = UUID.fromString(input.getBookItemId());
        } catch (IllegalArgumentException e) {
            log.error("Invalid bookItemId: {}", input.getBookItemId(), e);
            throw new IllegalArgumentException("Invalid book item ID", e);
        }

        BookItem bookItem = bookItemRepository.findById(bookItemId)
                .orElseThrow(() -> {
                    log.warn("Book Item with id {} not found.", input.getBookItemId());
                    return new BookNotFoundException("Book not found");
                });

        reader.getBookItemList().add(bookItem);
        readerRepository.save(reader);

        bookItem.setBookStatus(input.getBookStatus());
        bookItem.setReader(reader);
        bookItemRepository.save(bookItem);

        log.info("Book item with ID {} successfully lent to reader with email {}", input.getBookItemId(), input.getReaderEmail());

        return LendBookItemResult.builder()
                .bookStatus(bookItem.getBookStatus())
                .build();
    }
}