package com.Zi.spring_IMS.service;

import com.Zi.spring_IMS.model.dto.UserDTO;
import com.Zi.spring_IMS.model.entity.Account_Status;
import com.Zi.spring_IMS.model.entity.Role;
import com.Zi.spring_IMS.model.entity.User;
import com.Zi.spring_IMS.model.mapper.UserMapper;
import com.Zi.spring_IMS.repository.AuthRepository;
import com.Zi.spring_IMS.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;


    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthRepository authRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample User
        user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setUsername("johndoe@example.com");
        user.setPassword("hashedPassword");
        user.setRole(Role.STAFF);
        user.setStatus(Account_Status.ACTIVE);
        user.setCreated_at(LocalDateTime.now());
        user.setUpdate_at(LocalDateTime.now());

        // Sample UserDTO
        userDTO = UserDTO.builder()
                .name("John Doe")
                .username("johndoe@example.com")
                .password("NewPassword@123")
                .role(Role.MANAGER)
                .status(Account_Status.SUSPENDED)
                .build();
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> result = userService.getAllUsers();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUsername()).isEqualTo("johndoe@example.com");

        verify(userRepository).findAll();
    }

    @Test
    void testGetUserById_whenUserExists() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);

        verify(userRepository).findById(1);
    }

    @Test
    void testGetUserById_whenUserDoesNotExist() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("id is not found!");

        verify(userRepository).findById(1);
    }

    @Test
    void testUpdateUser_whenUserExists() {
        when(userRepository.findByUsername("johndoe@example.com")).thenReturn(Optional.of(user));
        when(userMapper.toUserEntity(userDTO)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updateUser(userDTO);

        assertThat(result).isNotNull();
        assertThat(result.getRole()).isEqualTo(Role.STAFF);

        verify(userRepository).findByUsername("johndoe@example.com");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testUpdateUser_whenUserDoesNotExist() {
        when(userRepository.findByUsername("johndoe@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateUser(userDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("username is not found!");

        verify(userRepository).findByUsername("johndoe@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUserById_whenUserExists() {
        when(userRepository.existsById(1)).thenReturn(true);

        Boolean result = userService.deleteUser(1);

        assertThat(result).isTrue();

        verify(userRepository).existsById(1);
        verify(userRepository).deleteById(1);
    }

    @Test
    void testDeleteUserById_whenUserDoesNotExist() {
        when(userRepository.existsById(1)).thenReturn(false);

        assertThatThrownBy(() -> userService.deleteUser(1))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("id is not found");

        verify(userRepository).existsById(1);
        verify(userRepository, never()).deleteById(anyInt());
    }
}
