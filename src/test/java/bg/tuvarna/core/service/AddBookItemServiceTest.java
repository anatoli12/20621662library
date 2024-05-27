package bg.tuvarna.core.service;

import bg.tuvarna.api.BookStatus;
import bg.tuvarna.api.operations.administrator.addbookitems.AddBookItemsInput;
import bg.tuvarna.api.operations.administrator.addbookitems.AddBookItemsResult;
import bg.tuvarna.persistence.entity.Book;
import bg.tuvarna.persistence.entity.BookItem;
import bg.tuvarna.persistence.repository.BookItemRepository;
import bg.tuvarna.persistence.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AddBookItemsServiceTest {

    @Mock
    private BookItemRepository bookItemRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AddBookItemsService addBookItemsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessBookNotFound() {
        String isbn = "1234567890";
        AddBookItemsInput input = AddBookItemsInput.builder()
                .isbn(isbn)
                .quantity(5)
                .build();
        when(bookRepository.findBookByIsbn(isbn)).thenReturn(Optional.empty());

        AddBookItemsResult result = addBookItemsService.process(input);

        assertEquals(false, result.getIsSuccessful());
        assertEquals("Book with ISBN " + isbn + " not found.", result.getMessage());
    }

    @Test
    void testProcessBookFound() {
        String isbn = "1234567890";
        Integer quantity = 5;
        AddBookItemsInput input = AddBookItemsInput.builder()
                .isbn(isbn)
                .quantity(quantity)
                .build();

        Book book = new Book();
        book.setIsbn(isbn);
        book.setId(UUID.randomUUID());
        when(bookRepository.findBookByIsbn(isbn)).thenReturn(Optional.of(book));

        AddBookItemsResult result = addBookItemsService.process(input);

        assertTrue(result.getIsSuccessful());
        assertEquals(quantity + " items added for book with ISBN " + isbn, result.getMessage());

        ArgumentCaptor<BookItem> bookItemCaptor = ArgumentCaptor.forClass(BookItem.class);
        verify(bookItemRepository, times(quantity)).save(bookItemCaptor.capture());

        for (BookItem savedBookItem : bookItemCaptor.getAllValues()) {
            assertEquals(book, savedBookItem.getBook());
            assertEquals(BookStatus.AVAILABLE, savedBookItem.getBookStatus());
        }
    }
}
