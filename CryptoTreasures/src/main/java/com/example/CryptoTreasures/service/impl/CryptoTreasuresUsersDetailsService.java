package com.example.CryptoTreasures.service.impl;

import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.repository.UserRepository;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;

public class CryptoTreasuresUsersDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CryptoTreasuresUsersDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new  UsernameNotFoundException("User" + username + "not found"));

       return map(user);

    }

    private UserDetails map(User user){


      return  org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .authorities("ROLE_" + user.getRole().name())
                .password(user.getPassword())

                .build();

    }


}
