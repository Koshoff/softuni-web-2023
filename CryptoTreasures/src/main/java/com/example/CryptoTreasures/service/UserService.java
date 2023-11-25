package com.example.CryptoTreasures.service;

import com.example.CryptoTreasures.model.dto.UserDTO;
import com.example.CryptoTreasures.model.entity.Article;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.model.ChangePasswordModel;
import com.example.CryptoTreasures.model.UserRegistrationModel;
import com.example.CryptoTreasures.model.enums.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserService {

    void register(UserRegistrationModel userRegistrationModel);

    boolean changePassword(ChangePasswordModel changePasswordModel);


    List<User> findUserByRole(Role role);

    List<User> findAllUsers();

    void banUser(Long id);

    void unbanUser(Long id);
    void makeModerator(Long id);
    void removeModerator(Long id);

    UserDTO findByUsername(String username);


    void promoteUsersToModerators() ;

}
