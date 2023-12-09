package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.UserRegistrationModel;
import com.example.CryptoTreasures.model.dto.ChangePasswordDTO;
import com.example.CryptoTreasures.model.dto.UserDTO;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.model.enums.Role;
import com.example.CryptoTreasures.model.events.UserRegisterEvent;
import com.example.CryptoTreasures.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;




    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register() {

        UserRegistrationModel userRegistrationModel = new UserRegistrationModel();
        userRegistrationModel.setUsername("user");
        userRegistrationModel.setPassword("password");
        userRegistrationModel.setEmail("user@example.com");

        User user = new User();
        when(modelMapper.map(userRegistrationModel, User.class)).thenReturn(user);

        // Изпълнение на метода
        userService.register(userRegistrationModel);

        // Проверка дали методите на мокнатите обекти са били извикани с правилните параметри
        verify(modelMapper).map(userRegistrationModel, User.class);
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(user);


    }

    @Test
    @WithMockUser(username="user")
    void changePassword() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setPassword(passwordEncoder.encode("oldPassword"));
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(user);
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        changePasswordDTO.setCurrentPassword("oldPassword");
        changePasswordDTO.setNewPassword("newPassword");
        changePasswordDTO.setConfirmNewPassword("newPassword");

        // Act
        boolean result = userService.changePassword(changePasswordDTO);

        // Assert
        assertTrue(result);
        // Проверка, че паролата на потребителя е била променена
        verify(userRepository).save(user);
    }

    @Test
    void findUserByRoleTest() {
        User user1 = new User();
        User user2 = new User();

        List<User> users = Arrays.asList(user1, user2);
        Role role = Role.USER;

        when(userRepository.findByRole(role)).thenReturn(users);

        UserDTO userDTO1 = new UserDTO();
        UserDTO userDTO2 = new UserDTO();
        when(modelMapper.map(user1, UserDTO.class)).thenReturn(userDTO1);
        when(modelMapper.map(user2, UserDTO.class)).thenReturn(userDTO2);


        List<UserDTO> result = userService.findUserByRole(role);


        assertEquals(2, result.size());
        assertTrue(result.containsAll(Arrays.asList(userDTO1, userDTO2)));
    }

    @Test
    void findAllUsersTest() {

        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        UserDTO userDTO1 = new UserDTO();
        UserDTO userDTO2 = new UserDTO();
        when(modelMapper.map(user1, UserDTO.class)).thenReturn(userDTO1);
        when(modelMapper.map(user2, UserDTO.class)).thenReturn(userDTO2);


        List<UserDTO> result = userService.findAllUsers();


        assertEquals(2, result.size());
        assertTrue(result.containsAll(Arrays.asList(userDTO1, userDTO2)));
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    void banUserTest() {
        Long id =  1L;
        User user = new User();
        user.setId(id);
        user.setRole(Role.BANNED);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        userService.banUser(id);

        verify(userRepository).save(user);
        assertEquals(Role.BANNED, user.getRole());
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    void unbanUserTest() {
        Long id =  1L;
        User user = new User();
        user.setId(id);
        user.setRole(Role.USER);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        userService.unbanUser(id);

        verify(userRepository).save(user);
        assertEquals(Role.USER, user.getRole());
    }


    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    void makeModeratorTest() {
        Long id =  1L;
        User user = new User();
        user.setId(id);
        user.setRole(Role.MODERATOR);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        userService.makeModerator(id);

        verify(userRepository).save(user);
        assertEquals(Role.MODERATOR, user.getRole());

    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    void removeModeratorTest() {
        Long id =  1L;
        User user = new User();
        user.setId(id);
        user.setRole(Role.USER);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        userService.removeModerator(id);

        verify(userRepository).save(user);
        assertEquals(Role.USER, user.getRole());
    }

    @Test
    void findByUsernameWhenUserExistsTest() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        UserDTO userDTO = new UserDTO();

        userDTO.setUsername(username);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO result = userService.findByUsername(username);

        assertNotNull(result);
        assertEquals(username, result.getUsername());

    }

    @Test
    void findByUsernameWhenUserDoesNotExistsTest() {
        String username = "Nobody";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            userService.findByUsername(username);
        });
    }

    @Test
    void promoteUsersToModerators() {
    }
}