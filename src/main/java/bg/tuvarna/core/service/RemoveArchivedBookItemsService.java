package bg.tuvarna.core.service;

import bg.tuvarna.api.BookStatus;
import bg.tuvarna.api.operations.administrator.removearchivedbooks.RemoveArchivedBookItems;
import bg.tuvarna.api.operations.administrator.removearchivedbooks.RemoveArchivedBookItemsInput;
import bg.tuvarna.api.operations.administrator.removearchivedbooks.RemoveArchivedBookItemsResult;
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
public class RemoveArchivedBookItemsService implements RemoveArchivedBookItems {
    private final BookItemRepository bookItemRepository;
    @Override
    @Transactional
    public RemoveArchivedBookItemsResult process(RemoveArchivedBookItemsInput input) {
        List<BookItem> archivedBookItems = bookItemRepository.findByBookStatus(BookStatus.ARCHIVED);
        int removedCount = archivedBookItems.size();

        bookItemRepository.deleteAll(archivedBookItems);
        archivedBookItems.forEach(bookItem -> log.info("Removed Book with ID: {}", bookItem.getId()));

        log.info("Removed {} archived book items", removedCount);

        return RemoveArchivedBookItemsResult.builder()
                .removedCount(removedCount)
                .build();
    }
}
