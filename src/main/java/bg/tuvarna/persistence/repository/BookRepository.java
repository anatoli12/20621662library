package bg.tuvarna.persistence.repository;

import bg.tuvarna.persistence.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findBookByIsbn(String isbn);
}
