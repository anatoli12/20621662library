package bg.tuvarna.persistence.repository;

import bg.tuvarna.persistence.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, UUID> {
}
