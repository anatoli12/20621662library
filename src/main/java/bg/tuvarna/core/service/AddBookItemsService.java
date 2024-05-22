package bg.tuvarna.core.service;

import bg.tuvarna.api.BookStatus;
import bg.tuvarna.api.operations.administrator.addbookitems.AddBookItems;
import bg.tuvarna.api.operations.administrator.addbookitems.AddBookItemsInput;
import bg.tuvarna.api.operations.administrator.addbookitems.AddBookItemsResult;
import bg.tuvarna.persistence.entity.Book;
import bg.tuvarna.persistence.entity.BookItem;
import bg.tuvarna.persistence.repository.BookItemRepository;
import bg.tuvarna.persistence.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AddBookItemsService implements AddBookItems {

    private final BookItemRepository bookItemRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public AddBookItemsResult process(AddBookItemsInput input) {
        Optional<Book> book = bookRepository.findBookByIsbn(input.getIsbn());

        if (book.isEmpty()) {
            return AddBookItemsResult.builder()
                    .isSuccessful(false)
                    .message("Book with ISBN " + input.getIsbn() + " not found.")
                    .build();
        }

        for (int i = 0; i < input.getQuantity(); i++) {
            bookItemRepository.save(BookItem.builder()
                    .book(book.get())
                    .bookStatus(BookStatus.AVAILABLE)
                    .build());
        }

        return AddBookItemsResult.builder()
                .isSuccessful(true)
                .message(input.getQuantity() + " items added for book with ISBN " + input.getIsbn())
                .build();
    }
}
