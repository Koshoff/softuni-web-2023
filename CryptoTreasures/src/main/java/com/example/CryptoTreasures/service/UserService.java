package com.example.CryptoTreasures.service;

import com.example.CryptoTreasures.model.dto.UserDTO;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.model.ChangePasswordModel;
import com.example.CryptoTreasures.model.UserRegistrationModel;
import com.example.CryptoTreasures.model.enums.Role;

import java.util.List;

public interface UserService {

    void register(UserRegistrationModel userRegistrationModel);
    boolean changePassword(ChangePasswordModel changePasswordModel);
    List<UserDTO> findUserByRole(Role roleEnum);
    List<UserDTO> findAllUsers();
    UserDTO findByUsername(String username);
    void promoteUsersToModerators() ;


    //TODO : изнасяне на методите в AdminService

    void banUser(Long id);
    void unbanUser(Long id);
    void makeModerator(Long id);
    void removeModerator(Long id);



}
