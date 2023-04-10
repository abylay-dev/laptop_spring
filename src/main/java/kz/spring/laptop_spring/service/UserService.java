package kz.spring.laptop_spring.service;

import kz.spring.laptop_spring.model.User;

import java.util.List;

public interface UserService {
    /*void upsertUser(User l, Integer id);*/

    User getUserById(Integer id);

    List<User> getAllUser();

    boolean getAllUsersOrderByLoginAsc(String login, String password);

    void deleteUser(Integer id);

    boolean registrationUser(String login, String password, String fio);

}
