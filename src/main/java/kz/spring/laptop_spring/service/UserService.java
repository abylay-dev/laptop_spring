package kz.spring.laptop_spring.service;

import kz.spring.laptop_spring.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUsers();

    User getUserByUsername(String username);

    User addUser(User user);

    boolean editUser(User user, Long roleId);

    boolean upsertUser(User user, String roleId);

    void resetPassword(String username);
}
