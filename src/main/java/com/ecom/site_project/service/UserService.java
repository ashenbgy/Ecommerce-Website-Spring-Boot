package com.ecom.site_project.service;

import com.ecom.site_project.entity.User;
import com.ecom.site_project.exception.UserNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    void saveUser(User user);

    void encodePassword(User user);

    String isLoginUnique(Integer id, String login);

    boolean checkLoginRegistration(String login);

    User getUser(int id) throws UserNotFoundException;

    User getUserByLogin(String login);

    void deleteUser(Integer id) throws UserNotFoundException;

    Page<User> listByPage(int pageNum);
}
