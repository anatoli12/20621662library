package bg.tuvarna.core.service;

import bg.tuvarna.api.BookStatus;
import bg.tuvarna.api.operations.operator.changebookstatus.ChangeBookStatusInput;
import bg.tuvarna.api.operations.operator.changebookstatus.ChangeBookStatusResult;
import bg.tuvarna.persistence.entity.BookItem;
import bg.tuvarna.persistence.repository.BookItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ChangeBookStatusServiceTest {

    @Mock
    private BookItemRepository bookItemRepository;

    @InjectMocks
    private ChangeBookStatusService changeBookStatusService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessInvalidBookItemId() {
        String invalidBookItemId = "invalid-uuid";
        ChangeBookStatusInput input = ChangeBookStatusInput.builder()
                .bookItemId(invalidBookItemId)
                .bookStatus(BookStatus.DAMAGED)
                .build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            changeBookStatusService.process(input);
        });

        assertEquals("Invalid book item ID", exception.getMessage());
    }

    @Test
    void testProcessBookItemNotFound() {
        String bookItemId = UUID.randomUUID().toString();
        ChangeBookStatusInput input = ChangeBookStatusInput.builder()
                .bookItemId(bookItemId)
                .bookStatus(BookStatus.DAMAGED)
                .build();

        when(bookItemRepository.findById(UUID.fromString(bookItemId))).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            changeBookStatusService.process(input);
        });

        assertEquals("Book item not found", exception.getMessage());
    }

    @Test
    void testProcessBookStatusChangedSuccessfully() {
        String bookItemId = UUID.randomUUID().toString();
        ChangeBookStatusInput input = ChangeBookStatusInput.builder()
                .bookItemId(bookItemId)
                .bookStatus(BookStatus.DAMAGED)
                .build();

        BookItem bookItem = new BookItem();
        bookItem.setId(UUID.fromString(bookItemId));
        bookItem.setBookStatus(BookStatus.AVAILABLE);

        when(bookItemRepository.findById(UUID.fromString(bookItemId))).thenReturn(Optional.of(bookItem));

        ChangeBookStatusResult result = changeBookStatusService.process(input);

        assertEquals(BookStatus.DAMAGED, result.getBookStatus());

        ArgumentCaptor<BookItem> bookItemCaptor = ArgumentCaptor.forClass(BookItem.class);
        verify(bookItemRepository, times(1)).save(bookItemCaptor.capture());

        BookItem savedBookItem = bookItemCaptor.getValue();
        assertEquals(BookStatus.DAMAGED, savedBookItem.getBookStatus());
    }
}
