package com.Zi.spring_IMS.config;

import com.Zi.spring_IMS.repository.AuthRepository;
import com.Zi.spring_IMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//This class, ApplicationConfig, configures essential beans for:
//
//        Fetching user details.
//        Password encoding.
//        Authentication provider setup.
//        Managing authentication.
@Configuration
public class ApplicationConfig {
    private final AuthRepository authRepository;

    @Autowired
    public ApplicationConfig(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }
    @Bean
    public UserDetailsService userDetailsService() {
        //fetch user from database
        return username -> authRepository
                .findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found!"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}
