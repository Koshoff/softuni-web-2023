package com.example.CryptoTreasures;

import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.model.enums.Role;
import com.example.CryptoTreasures.repository.UserRepository;
import com.example.CryptoTreasures.service.impl.CryptoTreasuresUsersDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CryptoTreasuresUserDetailsServiceTest {

 private CryptoTreasuresUsersDetailsService serviceToTest;
 @Mock
 private UserRepository mockUserRepository;
 @BeforeEach
 void setUp() {
  serviceToTest = new CryptoTreasuresUsersDetailsService(mockUserRepository);
 }
@Test
 void testUserNotFound() {
 Assertions.assertThrows(
         UsernameNotFoundException.class,
         () ->serviceToTest.loadUserByUsername("richi"));
}

 @Test
 void testUserFound() {
 //Arrange
  User user = createUser();
  when(mockUserRepository.findByUsername(user.getUsername()))
          .thenReturn(Optional.of(user));

  //Act
  UserDetails userDetails=serviceToTest.loadUserByUsername(user.getUsername());

  //Assert
  Assertions.assertNotNull(userDetails);
  Assertions.assertEquals("testUsername", user.getUsername());
  Assertions.assertEquals("test@email.com", user.getEmail());
  Assertions.assertEquals(user.getRole(), Role.USER);
  Assertions.assertEquals("testpassword", user.getPassword());


 }

 private static User createUser() {
  User user = new User();
  user.setUsername("testUsername");
  user.setEmail("test@email.com");
  user.setRole(Role.USER);
  user.setPassword("testpassword");
  return user;
 }

 }


