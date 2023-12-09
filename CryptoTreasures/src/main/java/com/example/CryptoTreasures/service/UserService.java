package com.example.CryptoTreasures.service;

import com.example.CryptoTreasures.model.dto.UserDTO;
import com.example.CryptoTreasures.model.dto.ChangePasswordDTO;
import com.example.CryptoTreasures.model.UserRegistrationModel;
import com.example.CryptoTreasures.model.enums.Role;

import java.util.List;

public interface UserService {

    void register(UserRegistrationModel userRegistrationModel);
    boolean changePassword(ChangePasswordDTO changePasswordDTO);
    List<UserDTO> findUserByRole(Role roleEnum);
    List<UserDTO> findAllUsers();
    UserDTO findByUsername(String username);
    void promoteUsersToModerators() ;

    boolean isUsernameOrEmailTaken(String username, String email);


    //TODO : изнасяне на методите в AdminService

    void banUser(Long id);
    void unbanUser(Long id);
    void makeModerator(Long id);
    void removeModerator(Long id);



}
