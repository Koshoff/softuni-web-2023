package com.example.CryptoTreasures.service;

import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.model.ChangePasswordModel;
import com.example.CryptoTreasures.model.UserRegistrationModel;
import com.example.CryptoTreasures.model.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void register(UserRegistrationModel userRegistrationModel);

    boolean changePassword(ChangePasswordModel changePasswordModel);

    //TODO : да заместя USER с DTO
    List<User> findUserByRole(Role role);

    List<User> findAllUsers();

    void banUser(Long id);

    void unbanUser(Long id);
    void makeModerator(Long id);
    void removeModerator(Long id);

    Optional<User> findByUsername(String username);

}
