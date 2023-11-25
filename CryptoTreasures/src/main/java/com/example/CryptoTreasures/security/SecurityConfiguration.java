package com.example.CryptoTreasures.security;

import com.example.CryptoTreasures.model.enums.Role;
import com.example.CryptoTreasures.repository.UserRepository;
import com.example.CryptoTreasures.service.impl.CryptoTreasuresUsersDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                authorizeRequests -> authorizeRequests
                        //Даваме достъп до всики статични ресурси , css , js , images
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        //Даваме достъп до home page, login , register
                        .requestMatchers("/", "/user/login", "/user/register", "/user/login-error").permitAll()
                        .requestMatchers("/banned", "/user/login").hasRole(Role.BANNED.name())
                        .requestMatchers("/admin/**").hasRole(Role.ADMIN.name())
                        //Всички други секции на сайта ще искат логване
                        .anyRequest().authenticated()


        )

                .formLogin(
                formLogin -> {
                    formLogin
                            //името на пътя , към който ще редиректнем ако достъпим ресурс , който
                            //не е позволен без да сме логнати.
                            .loginPage("/user/login")
                            //Това са имената на полетата в хтмл
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/about")
                            .failureHandler(((request, response, exception) -> {
                                String errorMessage;
                                if (exception instanceof DisabledException) {
                                    errorMessage = "Your account has been disabled.";
                                } else if (exception instanceof BadCredentialsException) {
                                    errorMessage = "Invalid username or password.";
                                } else {
                                    errorMessage = "Login error. Please try again.";
                                }
                                request.getSession().setAttribute("loginError", errorMessage);
                                response.sendRedirect("/user/login");
                            }));



                }
        ).logout(
                logout -> {
                    logout
                            //Мапинга за логоут
                            .logoutUrl("/logout")
                            //Къде ще отиде след като се разлогне
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true);

                }
        );

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){

        /*
        Ползваме този service , за да може spring security да разбира нашите
        потребители и роли.
         */
        return new CryptoTreasuresUsersDetailsService(userRepository);
    }


}
