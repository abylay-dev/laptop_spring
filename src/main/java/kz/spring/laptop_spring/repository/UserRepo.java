package kz.spring.laptop_spring.repository;

import kz.spring.laptop_spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByUsername(String username);

    User deleteUserByUsername(String username);
}
