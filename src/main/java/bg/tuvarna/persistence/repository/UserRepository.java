package bg.tuvarna.persistence.repository;

import bg.tuvarna.api.UserAuthority;
import bg.tuvarna.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);

    List<User> findAllByUserAuthority(UserAuthority userAuthority);
}
