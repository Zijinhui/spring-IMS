package com.Zi.spring_IMS.model.mapper;

import com.Zi.spring_IMS.model.dto.AuthRegisterDTO;
import com.Zi.spring_IMS.model.dto.UserDTO;
import com.Zi.spring_IMS.model.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {
    // update existing User entity wth fields from UserDTO
    public User toUserEntity(UserDTO userDTO) {
        User user = new User();

        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setStatus(userDTO.getStatus());
        user.setUpdate_at(LocalDateTime.now());

        return user;
    }

    // map AuthRegisterDTO to User entity
    public User toUserEntity(AuthRegisterDTO authRegisterDTO) {
        User newUser = new User();

        newUser.setName(authRegisterDTO.getName());
        newUser.setUsername(authRegisterDTO.getUsername());
        newUser.setPassword(authRegisterDTO.getPassword());
        newUser.setCreated_at(LocalDateTime.now());
        newUser.setUpdate_at(LocalDateTime.now());

        return newUser;
    }

}
