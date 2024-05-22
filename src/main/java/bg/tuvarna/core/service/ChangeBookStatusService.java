package bg.tuvarna.core.service;

import bg.tuvarna.api.operations.operator.changebookstatus.ChangeBookStatus;
import bg.tuvarna.api.operations.operator.changebookstatus.ChangeBookStatusInput;
import bg.tuvarna.api.operations.operator.changebookstatus.ChangeBookStatusResult;
import bg.tuvarna.persistence.entity.BookItem;
import bg.tuvarna.persistence.repository.BookItemRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChangeBookStatusService implements ChangeBookStatus {
    private static final Logger logger = LoggerFactory.getLogger(ChangeBookStatusService.class);
    private final BookItemRepository bookItemRepository;

    @Override
    @Transactional
    public ChangeBookStatusResult process(ChangeBookStatusInput input) {
        logger.info("Processing change book status for bookItemId: {}", input.getBookItemId());

        UUID bookItemId;
        try {
            bookItemId = UUID.fromString(input.getBookItemId());
        } catch (Exception e) {
            logger.error("Invalid bookItemId: {}", input.getBookItemId(), e);
            throw new IllegalArgumentException("Invalid book item ID", e);
        }

        Optional<BookItem> bookItemOptional = bookItemRepository.findById(bookItemId);
        if (bookItemOptional.isEmpty()) {
            logger.error("BookItem with ID {} not found", bookItemId);
            throw new IllegalArgumentException("Book item not found");
        }

        BookItem bookItem = bookItemOptional.get();
        bookItem.setBookStatus(input.getBookStatus());

        bookItemRepository.save(bookItem);

        logger.info("Book status changed successfully for bookItemId: {}", bookItemId);

        return ChangeBookStatusResult.builder()
                .bookStatus(bookItem.getBookStatus())
                .build();
    }
}
