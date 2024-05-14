package bg.tuvarna.persistence.repository;

import bg.tuvarna.persistence.entity.BookItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookItemRepository extends JpaRepository<BookItem, UUID> {
}
