package cz.javajitsu.my_app.repos;

import cz.javajitsu.my_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
