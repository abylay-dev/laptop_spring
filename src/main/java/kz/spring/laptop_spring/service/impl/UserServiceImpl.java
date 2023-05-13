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

import java.util.HashSet;
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
    public User addUser(User user) {
        return null;
    }


    @Override
    public boolean editUser(User user, Long roleId) {

        User userFromDB = userRepository.findUserByUsername(user.getUsername());

        if (userFromDB == null) {
            return false;
        }
        else {
            Role role = roleRepository.findById(roleId).get();
            HashSet<Role> roles = new HashSet<>();
            roles.add(role);
            userFromDB.setRoles(roles);
            System.out.println("uygu626 :: "+userFromDB);

            userRepository.save(userFromDB);

            return true;
        }
    }

    @Override
    public boolean upsertUser(User user, String roleId) {
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
    public boolean deleteUser(String username) {
        if (userRepository.findUserByUsername(username) != null) {
            userRepository.deleteUserByUsername(username);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
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

    @Override
    public void resetPassword(String username) {
        User userFromDb = getUserByUsername(username);
        userFromDb.setPassword(passwordEncoder.encode("1234"));
        userRepository.save(userFromDb);
    }
}
