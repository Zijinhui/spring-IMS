package com.Zi.spring_IMS.service;

import com.Zi.spring_IMS.model.dto.UserDTO;
import com.Zi.spring_IMS.model.entity.Auth;
import com.Zi.spring_IMS.model.entity.User;
import com.Zi.spring_IMS.model.mapper.UserMapper;
import com.Zi.spring_IMS.repository.AuthRepository;
import com.Zi.spring_IMS.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() { return userRepository.findAll(); }

    public User getUserById(Integer id) { return userRepository.findById(id).orElseThrow(()-> new RuntimeException("id is not found!")); }

    public User updateUser(UserDTO userDTO) {
        // fetch the existing user by username
        User existingUser = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(()-> new RuntimeException("username is not found!"));

        if (!existingUser.getUsername().equals(userDTO.getUsername())) {
            throw new IllegalArgumentException("Username cannot be updated");
        }

        // convert UserDTO to User Entity
        User updatedUser = userMapper.toUserEntity(userDTO);

        // update allowed fields
        existingUser.setName(updatedUser.getName());
        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        existingUser.setRole(updatedUser.getRole());
        existingUser.setStatus(updatedUser.getStatus());
        existingUser.setUpdate_at(updatedUser.getUpdate_at());


        // update Auth entity
        Auth authUser = authRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(()-> new RuntimeException("id is not found!")); // assume user must exist 'cause this feature only available after user login
        authUser.setPassword(userDTO.getPassword());
        authRepository.save(authUser);

        return userRepository.save(existingUser);
    }

    public Boolean deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("id is not found");
        }
        userRepository.deleteById(id);
        authRepository.deleteById(id);
        return true;
    }

}
