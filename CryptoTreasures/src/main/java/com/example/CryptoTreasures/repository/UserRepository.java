package com.example.CryptoTreasures.repository;

import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findByUsernameOrEmail(String username, String email);
     Optional<User> findByUsername(String username);
     List<User> findByRole(Role roleEnum);

     List<User> findByDateCreatedBefore(LocalDate date);
}
