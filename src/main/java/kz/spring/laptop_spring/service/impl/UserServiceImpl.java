package kz.spring.laptop_spring.service.impl;

import kz.spring.laptop_spring.model.Role;
import kz.spring.laptop_spring.model.User;
import kz.spring.laptop_spring.repository.RoleRepo;
import kz.spring.laptop_spring.repository.UserRepo;
import kz.spring.laptop_spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepository;

    @Autowired
    private RoleRepo roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public boolean upsertUser(User user) {
        User userFromDB = userRepository.findUserByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        if (user.getPassword().equals(user.getRePassword())) {

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role role = roleRepository.findRoleByName("ROLE_USER");
            user.setRoles(Set.of(role));

            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(Long id) {
        if (userRepository.getReferenceById(id) != null) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null || !user.getUsername().equals(username)) throw new UsernameNotFoundException("User not found");
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles());
    }
}
