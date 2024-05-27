package bg.tuvarna.persistence.repository;

import bg.tuvarna.api.BookStatus;
import bg.tuvarna.persistence.entity.Book;
import bg.tuvarna.persistence.entity.BookItem;
import bg.tuvarna.persistence.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookItemRepository extends JpaRepository<BookItem, UUID> {
    List<BookItem> findAllByBook(Book book);

    List<BookItem> findByBookStatus(BookStatus bookStatus);
    List<BookItem> findAllByReader(Reader reader);
}
