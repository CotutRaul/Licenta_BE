package uvt.cotut.licenta_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uvt.cotut.licenta_be.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String username);
}
