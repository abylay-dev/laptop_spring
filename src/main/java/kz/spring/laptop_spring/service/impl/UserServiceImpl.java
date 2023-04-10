package kz.spring.laptop_spring.service.impl;

import kz.spring.laptop_spring.model.User;
import kz.spring.laptop_spring.repository.UserRepo;
import kz.spring.laptop_spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepository;


    @Override
    public User getUserById(Integer id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public boolean getAllUsersOrderByLoginAsc(String login, String password) {
        List<User> users = userRepository.findAll();
        for (User u : users) {
            if (u.getLogin().equals(login) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean registrationUser(String login, String password, String fio) {
        try{
            userRepository.save(new User(null, login, password, fio));
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

}
