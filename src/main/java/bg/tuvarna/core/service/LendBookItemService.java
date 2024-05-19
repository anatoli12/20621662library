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

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LendBookItemService implements LendBookItem {

    private final ReaderRepository readerRepository;
    private final BookItemRepository bookItemRepository;

    @Override
    public LendBookItemResult process(LendBookItemInput input) {
        Optional<Reader> reader = readerRepository.findByEmail(input.getReaderEmail());

        if (reader.isEmpty()) {
            log.warn("Reader with email {} not found.", input.getReaderEmail());
            throw new UserNotFoundException("User with email not found");
        }

        Optional<BookItem> bookItem = bookItemRepository.findById(UUID.fromString(input.getBookItemId()));

        if (bookItem.isEmpty()) {
            log.warn("Book Item with id {} not found.", input.getBookItemId());
            throw new BookNotFoundException("Book not found");
        }

        reader.get().getBookItemList().add(bookItem.get());
        readerRepository.save(reader.get());

        bookItem.get().setBookStatus(input.getBookStatus());
        bookItem.get().setReader(reader.get());
        bookItemRepository.save(bookItem.get());

        return LendBookItemResult.builder()
                .bookStatus(bookItem.get().getBookStatus())
                .build();
    }
}
