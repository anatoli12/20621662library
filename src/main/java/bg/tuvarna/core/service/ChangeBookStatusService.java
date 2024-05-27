package bg.tuvarna.core.service;

import bg.tuvarna.api.BookStatus;
import bg.tuvarna.api.exception.BookNotFoundException;
import bg.tuvarna.api.operations.operator.changebookstatus.ChangeBookStatus;
import bg.tuvarna.api.operations.operator.changebookstatus.ChangeBookStatusInput;
import bg.tuvarna.api.operations.operator.changebookstatus.ChangeBookStatusResult;
import bg.tuvarna.persistence.entity.BookItem;
import bg.tuvarna.persistence.repository.BookItemRepository;
import bg.tuvarna.persistence.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChangeBookStatusService implements ChangeBookStatus {
    private static final Logger logger = LoggerFactory.getLogger(ChangeBookStatusService.class);
    private final BookRepository bookRepository;
    private final BookItemRepository bookItemRepository;

    @Override
    @Transactional
    public ChangeBookStatusResult process(ChangeBookStatusInput input) {
        logger.info("Processing change book status for ISBN: {}", input.getIsbn());

        Optional<BookItem> bookItemOptional = bookItemRepository.findByBookIsbn(input.getIsbn())
                .stream()
                .filter(bookItem -> bookItem.getBookStatus() == BookStatus.AVAILABLE)
                .findFirst();

        if (bookItemOptional.isEmpty()) {
            logger.error("No available book item found for ISBN: {}", input.getIsbn());
            throw new BookNotFoundException("Book not found");
        }

        BookItem bookItem = bookItemOptional.get();
        bookItem.setBookStatus(input.getBookStatus());

        bookItemRepository.save(bookItem);

        logger.info("Book status changed successfully for ISBN: {}", input.getIsbn());

        return ChangeBookStatusResult.builder()
                .bookStatus(bookItem.getBookStatus())
                .build();
    }
}

