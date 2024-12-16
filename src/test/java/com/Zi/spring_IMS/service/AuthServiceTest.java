package com.Zi.spring_IMS.service;


import com.Zi.spring_IMS.config.JwtService;
import com.Zi.spring_IMS.model.dto.AuthLoginDTO;
import com.Zi.spring_IMS.model.dto.AuthRegisterDTO;
import com.Zi.spring_IMS.model.dto.AuthResponseDTO;
import com.Zi.spring_IMS.model.entity.Account_Status;
import com.Zi.spring_IMS.model.entity.Auth;
import com.Zi.spring_IMS.model.entity.Role;
import com.Zi.spring_IMS.model.entity.User;
import com.Zi.spring_IMS.model.mapper.AuthMapper;
import com.Zi.spring_IMS.model.mapper.UserMapper;
import com.Zi.spring_IMS.repository.AuthRepository;
import com.Zi.spring_IMS.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthServiceTest {
    @Mock
    private AuthRepository authRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthMapper authMapper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    /**
     *  Initialize the mocks and inject them into the test class.
     *  Ensure that the mock objects and their behaviors are properly created and associated
     *  with the fields annotated with @Mock or @InjectMocks.
     */
    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginAuthentication_whenValidCredentials() {
        AuthLoginDTO authLoginDTO = new AuthLoginDTO("testuser@example.com", "password123");
        Auth authUser = new Auth();
        authUser.setUsername("testuser@example.com");
        authUser.setPassword("hashedpassword");
        authUser.setRole(Role.STAFF);
        authUser.setStatus(Account_Status.ACTIVE);

        when(authRepository.findByUsername("testuser@example.com")).thenReturn(Optional.of(authUser));
        when(jwtService.generateToken(authUser)).thenReturn("mocked-jwt-token");

        AuthResponseDTO response = authService.loginAuthentication(authLoginDTO);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("mocked-jwt-token");

        verify(authRepository).findByUsername("testuser@example.com");
        verify(jwtService).generateToken(authUser);
    }

    @Test
    void loginAuthentication_whenInvalidCredentials() {
        AuthLoginDTO authLoginDTO = new AuthLoginDTO("invaliduser@example.com", "password123");

        when(authRepository.findByUsername("invaliduser@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.loginAuthentication(authLoginDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User not found with email: invaliduser@example.com");

        verify(authRepository).findByUsername("invaliduser@example.com");
        verifyNoInteractions(jwtService); // token generation should not be invoked
    }

    @Test
    void registerAuthentication_whenUsernameAlreadyExists() {
        AuthRegisterDTO authRegisterDTO = AuthRegisterDTO.builder()
                .name("Test User")
                .username("testuser@example.com")
                .password("Password@123")
                .build();

        when(authRepository.findByUsername("testuser@example.com")).thenReturn(Optional.of(new Auth()));

        assertThatThrownBy(() -> authService.registerAuthentication(authRegisterDTO))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Username already exist! Please try another one ^_^");

        verify(authRepository, never()).save(any(Auth.class)); // check Auth entity never save this user
        verify(userRepository, never()).save(any(User.class)); // check User entity never save this user
//        verifyNoInteractions(userRepository);
//        verifyNoInteractions(jwtService);
    }

    @Test
    void registerAuthentication_whenValidInput() {
        AuthRegisterDTO authRegisterDTO = AuthRegisterDTO.builder()
                .name("Test User")
                .username("testuser@example.com")
                .password("Password@123")
                .build();

        Auth newAuth = new Auth();
        newAuth.setId(1);
        newAuth.setUsername("testuser@example.com");
        newAuth.setPassword("hashedpassword");
        newAuth.setRole(Role.STAFF);
        newAuth.setStatus(Account_Status.ACTIVE);

        User newUser = new User();
        newUser.setId(1);
        newUser.setName("Test User");
        newUser.setUsername("testuser@example.com");
        newUser.setPassword("hashedpassword");
        newUser.setRole(Role.STAFF);
        newUser.setCreated_at(LocalDateTime.now());
        newUser.setUpdate_at(LocalDateTime.now());
        newUser.setStatus(Account_Status.ACTIVE);

        when(authRepository.findByUsername("testuser@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("Password@123")).thenReturn("hashedpassword");
        when(authMapper.toAuthEntity(authRegisterDTO)).thenReturn(newAuth);
        when(userMapper.toUserEntity(authRegisterDTO)).thenReturn(newUser);
        when(jwtService.generateToken(newAuth)).thenReturn("mocked-jwt-token");

        AuthResponseDTO response = authService.registerAuthentication(authRegisterDTO);
        //AuthRepository authRepository = mock(AuthRepository.class);

        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("mocked-jwt-token");

        // verify Auth entity was saved correctly
        verify(authRepository).save(argThat(auth ->
                auth.getUsername().equals("testuser@example.com") &&
                        auth.getPassword().equals("hashedpassword") &&
                        auth.getRole() == Role.STAFF &&
                        auth.getStatus() == Account_Status.ACTIVE
        ));

        // verify User entity was saved correctly
        verify(userRepository).save(argThat(user ->
                user.getName().equals("Test User") &&
                        user.getUsername().equals("testuser@example.com") &&
                        user.getPassword().equals("hashedpassword") &&
                        user.getRole() == Role.STAFF &&
                        user.getStatus() == Account_Status.ACTIVE
        ));

        // Verify token generation
        verify(jwtService).generateToken(newAuth);
    }

    //need to be fixed
//    @Test
//    void registerAuthentication_passwordHashing() {
//        AuthRegisterDTO authRegisterDTO = AuthRegisterDTO.builder()
//                .name("Test User")
//                .username("newuser@example.com")
//                .password("Password@123")
//                .build();
//        Auth newAuth = new Auth();
//        newAuth.setId(1);
//        newAuth.setUsername("newuser@example.com");
//        newAuth.setPassword("hashedpassword");
//        newAuth.setRole(Role.STAFF);
//        newAuth.setStatus(Account_Status.ACTIVE);
//
//        User newUser = new User();
//        newUser.setId(1);
//        newUser.setName("Test User");
//        newUser.setUsername("newuser@example.com");
//        newUser.setPassword("hashedpassword");
//        newUser.setRole(Role.STAFF);
//        newUser.setCreated_at(LocalDateTime.now());
//        newUser.setUpdate_at(LocalDateTime.now());
//        newUser.setStatus(Account_Status.ACTIVE);
//
//        when(authRepository.findByUsername("newuser@example.com")).thenReturn(Optional.empty());
//        when(passwordEncoder.encode("Password@123")).thenReturn("hashedpassword");
//        when(authMapper.toAuthEntity(authRegisterDTO)).thenReturn(newAuth); // Mock mapper behavior
//        when(userMapper.toUserEntity(authRegisterDTO)).thenReturn(newUser);
//
//       AuthResponseDTO result = authService.registerAuthentication(authRegisterDTO);
//
//        assertThat(result).isNotNull();
//        assertThat(result.getToken()).isNotEmpty(); // Verify token is generated
//
//        verify(passwordEncoder).encode("Password@123");
//        verify(authRepository).save(argThat(auth -> auth.getPassword().equals("hashedpassword")));
//
//        // verify Auth entity saved with correct data
//        verify(authRepository).save(argThat(auth ->
//                auth.getUsername().equals("newuser@example.com") &&
//                        auth.getPassword().equals("hashedpassword") &&
//                        auth.getRole() == Role.STAFF &&
//                        auth.getStatus() == Account_Status.ACTIVE
//        ));
//    }

}
