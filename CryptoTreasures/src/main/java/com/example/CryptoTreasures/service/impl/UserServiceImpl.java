package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.dto.UserDTO;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.model.dto.ChangePasswordDTO;
import com.example.CryptoTreasures.model.UserRegistrationModel;
import com.example.CryptoTreasures.model.enums.Role;
import com.example.CryptoTreasures.model.events.UserRegisterEvent;
import com.example.CryptoTreasures.repository.UserRepository;
import com.example.CryptoTreasures.service.UserService;
import com.example.CryptoTreasures.util.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ModelMapper modelMapper;

    public UserServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           ApplicationEventPublisher applicationEventPublisher, ModelMapper modelMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.modelMapper = modelMapper;
    }

    @Override
    public void register(UserRegistrationModel userRegistrationModel) {
        User user = modelMapper.map(userRegistrationModel, User.class);
        user.setPassword(passwordEncoder.encode(userRegistrationModel.getPassword()));
        user.setDateCreated(LocalDate.now());
        user.setRole(Role.USER);
        user.setEmail(userRegistrationModel.getEmail());
        userRepository.save(user);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public boolean changePassword(ChangePasswordDTO changePasswordDTO) {
        User user = userRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);
        if(user != null){
            if(passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword())){
                if(changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmNewPassword())){
                    user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
                    userRepository.save(user);
                    return true;
                }
        }
        }
        return false;
    }

    @Override
    public List<UserDTO> findUserByRole(Role roleEnum) {
        return userRepository.findByRole(roleEnum)
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void banUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setRole(Role.BANNED);
            userRepository.save(user);
        });

    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void unbanUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setRole(Role.USER);
            userRepository.save(user);
        });
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void makeModerator(Long id) {
        userRepository.findById(id).ifPresent(user ->{
            user.setRole(Role.MODERATOR);
            userRepository.save(user);
        });
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void removeModerator(Long id) {
        userRepository.findById(id).ifPresent(user ->{
            user.setRole(Role.USER);
            userRepository.save(user);
        });
    }

    @Override
    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow((() -> new EntityNotFoundException(
                "User with username: " + username + "not found")));
        return modelMapper.map(user, UserDTO.class);
    }
    @Override
    public void promoteUsersToModerators() {
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);
        List<User> usersToPromote = userRepository.findByDateCreatedBefore(oneWeekAgo);
        usersToPromote
                .forEach(user -> {
                    if(user.getRole().name().equals("USER")){
                        user.setRole(Role.MODERATOR);
                        userRepository.save(user);
                    }
                });
    }

    @Override
    public boolean isUsernameOrEmailTaken(String username, String email) {
        return userRepository.existsByUsernameOrEmail(username, email);
    }
    private  User map(UserRegistrationModel userRegistrationModel){
        User user = userRepository.findByUsernameOrEmail(userRegistrationModel.getUsername(), userRegistrationModel.getEmail()).orElse(null);
        if(user == null){
            user = new User();
            user.setUsername(userRegistrationModel.getUsername());
            user.setEmail(userRegistrationModel.getEmail());
            user.setPassword(passwordEncoder.encode(userRegistrationModel.getPassword()));
            user.setDateCreated(LocalDate.now());
            user.setRole(Role.USER);
            user.setEnabled(false);
        }
        return user;
    }
}
