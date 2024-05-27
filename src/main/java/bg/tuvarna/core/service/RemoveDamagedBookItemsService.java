package bg.tuvarna.core.service;

import bg.tuvarna.api.BookStatus;
import bg.tuvarna.api.operations.administrator.removedamagedbooks.RemoveDamagedBookItems;
import bg.tuvarna.api.operations.administrator.removedamagedbooks.RemoveDamagedBookItemsInput;
import bg.tuvarna.api.operations.administrator.removedamagedbooks.RemoveDamagedBookItemsResult;
import bg.tuvarna.persistence.entity.BookItem;
import bg.tuvarna.persistence.repository.BookItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RemoveDamagedBookItemsService implements RemoveDamagedBookItems {
    private final BookItemRepository bookItemRepository;
    @Override
    @Transactional
    public RemoveDamagedBookItemsResult process(RemoveDamagedBookItemsInput input) {
        List<BookItem> damagedBookItems = bookItemRepository.findByBookStatus(BookStatus.DAMAGED);
        int removedCount = damagedBookItems.size();

        bookItemRepository.deleteAll(damagedBookItems);
        damagedBookItems.forEach(bookItem -> log.info("Removed Book with ID: {}", bookItem.getId()));


        log.info("Removed {} damaged book items", removedCount);

        return RemoveDamagedBookItemsResult.builder()
                .removedCount(removedCount)
                .build();
    }
}
