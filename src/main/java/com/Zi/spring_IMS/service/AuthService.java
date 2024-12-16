package com.Zi.spring_IMS.service;

import com.Zi.spring_IMS.config.JwtService;
import com.Zi.spring_IMS.model.dto.AuthLoginDTO;
import com.Zi.spring_IMS.model.dto.AuthRegisterDTO;
import com.Zi.spring_IMS.model.dto.AuthResponseDTO;
import com.Zi.spring_IMS.model.entity.Auth;
import com.Zi.spring_IMS.model.entity.User;
import com.Zi.spring_IMS.model.mapper.AuthMapper;
import com.Zi.spring_IMS.model.mapper.UserMapper;
import com.Zi.spring_IMS.repository.AuthRepository;
import com.Zi.spring_IMS.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Autowired
    public AuthService(AuthRepository authRepository,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       PasswordEncoder passwordEncoder,
                       AuthMapper authMapper,
                       UserMapper userMapper,
                       UserRepository userRepository) {
        this.authRepository = authRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authMapper = authMapper;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }


    // backend server only authenticate if this user(who send this request) has token
    // for Login
    public AuthResponseDTO loginAuthentication(AuthLoginDTO authLoginDTO) {
        // validates credentials against the UserDetailsService and PasswordEncoder
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authLoginDTO.getUsername(),
                        authLoginDTO.getPassword()
                )
        );

        // fetch user
        // check if this user exists in auth table (database)
        Auth authUser = authRepository.findByUsername(authLoginDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + authLoginDTO.getUsername()));

        String jwtToken = jwtService.generateToken(authUser);
        return AuthResponseDTO.builder()
                .token(jwtToken)
                .build();
    }

    // for register
    public AuthResponseDTO registerAuthentication(AuthRegisterDTO authRegisterDTO) {
        // check if the username already exists
        if (authRepository.findByUsername(authRegisterDTO.getUsername()).isPresent()) {
            throw new IllegalStateException("Username already exist! Please try another one ^_^");
        }

        // hash password
        authRegisterDTO.setPassword(passwordEncoder.encode(authRegisterDTO.getPassword()));

        // create a new Auth entity
        Auth newAuth = authMapper.toAuthEntity(authRegisterDTO);
        authRepository.save(newAuth);

        // create new User entity (Synchronous Update)
        User newUser = userMapper.toUserEntity(authRegisterDTO);
        userRepository.save(newUser);

        System.out.println("Password in DTO: " + authRegisterDTO.getPassword());
        System.out.println("Password in Auth: " + newAuth.getPassword());
        System.out.println("Password in User: " + newUser.getPassword());

        // generate new JWT token for this new Auth
        String jwtToken = jwtService.generateToken(newAuth);
        return AuthResponseDTO.builder()
                .token(jwtToken)
                .build();
    }

}

