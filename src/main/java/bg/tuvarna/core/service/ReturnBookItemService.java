package bg.tuvarna.core.service;

import bg.tuvarna.api.BookStatus;
import bg.tuvarna.api.operations.operator.returnbook.ReturnBookItem;
import bg.tuvarna.api.operations.operator.returnbook.ReturnBookItemInput;
import bg.tuvarna.api.operations.operator.returnbook.ReturnBookItemResult;
import bg.tuvarna.persistence.entity.BookItem;
import bg.tuvarna.persistence.entity.Reader;
import bg.tuvarna.persistence.repository.BookItemRepository;
import bg.tuvarna.persistence.repository.ReaderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReturnBookItemService implements ReturnBookItem {
    private final ReaderRepository readerRepository;
    private final BookItemRepository bookItemRepository;
    private final Random random = new Random();

    @Override
    public ReturnBookItemResult process(ReturnBookItemInput input) {
        BookItem bookItemToReturn = bookItemRepository.findById(UUID.fromString(input.getBookItemId())).get();
        Reader reader = bookItemToReturn.getReader();

        // Implementing a 15% chance that the book is destroyed
        boolean isDestroyed = random.nextInt(100) < 15;

        if (isDestroyed) {
            bookItemToReturn.setBookStatus(BookStatus.DAMAGED);
            reader.setReaderRating(reader.getReaderRating()-2);
            log.info("Book item with ID {} was damaged upon return by reader with email {}", bookItemToReturn.getId(), reader.getEmail());
        } else {
            bookItemToReturn.setBookStatus(BookStatus.AVAILABLE);
            reader.setReaderRating(reader.getReaderRating()+1);
            log.info("Book item with ID {} returned successfully by reader with email {}", bookItemToReturn.getId(), reader.getEmail());
        }
        bookItemToReturn.setReader(null);

        bookItemRepository.save(bookItemToReturn);
        readerRepository.save(reader);

        log.info("Book item with ID {} returned successfully by reader with email {}", bookItemToReturn.getId(), reader.getEmail());

        return ReturnBookItemResult.builder()
                .isSuccessful(true)
                .build();
    }
}
