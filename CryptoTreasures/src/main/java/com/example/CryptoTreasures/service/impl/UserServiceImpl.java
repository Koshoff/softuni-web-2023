package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.model.ChangePasswordModel;
import com.example.CryptoTreasures.model.UserRegistrationModel;
import com.example.CryptoTreasures.model.enums.Role;
import com.example.CryptoTreasures.model.events.UserRegisterEvent;
import com.example.CryptoTreasures.repository.UserRepository;
import com.example.CryptoTreasures.service.UserService;
import com.example.CryptoTreasures.util.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
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
        userRepository.save(user);

        applicationEventPublisher.publishEvent(new UserRegisterEvent("UserService", userRegistrationModel.getUsername()));
    }

    @Override
    public boolean changePassword(ChangePasswordModel changePasswordModel) {
        User user = userRepository.findByUsername(SecurityUtils.getCurrentUsername()).orElse(null);
        if(user != null){
            if(passwordEncoder.matches(changePasswordModel.getCurrentPassword(), user.getPassword())){
                if(changePasswordModel.getNewPassword().equals(changePasswordModel.getConfirmNewPassword())){
                    user.setPassword(passwordEncoder.encode(changePasswordModel.getNewPassword()));
                    userRepository.save(user);
                    return true;
                }
        }
        }
        return false;
    }



    @Override
    public List<User> findUserByRole(Role role) {
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void banUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setRole(Role.BANNED);
            userRepository.save(user);
        });

    }

    @Override
    public void unbanUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setRole(Role.USER);
            userRepository.save(user);
        });
    }

    @Override
    public void makeModerator(Long id) {
        userRepository.findById(id).ifPresent(user ->{
            user.setRole(Role.MODERATOR);
            userRepository.save(user);
        });
    }

    @Override
    public void removeModerator(Long id) {
        userRepository.findById(id).ifPresent(user ->{
            user.setRole(Role.USER);
            userRepository.save(user);
        });
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
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
            user.setActive(false);



        }
        return user;
    }
}
