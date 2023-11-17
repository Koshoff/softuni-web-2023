package com.example.CryptoTreasures.init;

import com.example.CryptoTreasures.model.entity.Category;
import com.example.CryptoTreasures.model.entity.User;
import com.example.CryptoTreasures.model.enums.Role;
import com.example.CryptoTreasures.repository.CategoryRepository;
import com.example.CryptoTreasures.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;


    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findByRole(Role.ADMIN).isEmpty()){
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("Softuni2023"));
            admin.setDateCreated(LocalDate.now());
            admin.setEmail("admin@email.com");
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }

        if(categoryRepository.count() == 0){
            categoryRepository.save(new Category("Cryptocurrencies", "Cryptocurrencies are digital or virtual currencies that use cryptography for security and operate independently of a central bank or institution."));
            categoryRepository.save(new Category("NFT", "An NFT is a type of cryptographic token that represents a unique asset (e.g., art, events, in-game items) and cannot be exchanged on a one-to-one basis or with identical value/identity." ));
            categoryRepository.save(new Category("DeFi", "DeFi refers to financial services that are utilized without traditional banking, brokerage, or other intermediary institutions.\n" +
                    "They are built on blockchain technologies, primarily using the Ethereum blockchain."));
        }

        if (userRepository.count() == 1) { // Проверка дали има записи в базата данни
            for (int i = 1; i <= 10; i++) {
                User user = new User();
                user.setUsername("User" + i);
                user.setEmail("user" + i + "@email.com");
                user.setPassword(passwordEncoder.encode("password123"));
                user.setDateCreated(LocalDate.now());

                if (i == 4 || i == 7) {
                    user.setRole(Role.BANNED);
                } else {
                    user.setRole(Role.USER);
                }
                userRepository.save(user);
            }
        }

    }
}
