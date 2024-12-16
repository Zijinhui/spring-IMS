package com.Zi.spring_IMS.model.mapper;

import com.Zi.spring_IMS.model.dto.AuthRegisterDTO;
import com.Zi.spring_IMS.model.entity.Auth;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
//    //convert AuthRequestDTO to Auth entity
//    public Auth toAuthEntity(AuthLoginDTO authLoginDTO) {
//        Auth auth = new Auth();
//
//        auth.setUsername(authLoginDTO.getUsername());
//        auth.setPassword(authLoginDTO.getPassword());
//
//        return auth;
//    }

    public Auth toAuthEntity(AuthRegisterDTO authRegisterDTO) {
        Auth newUser = new Auth();
        // use the already hashed password from the DTO
        newUser.setUsername(authRegisterDTO .getUsername());
        newUser.setPassword(authRegisterDTO.getPassword()); // now this is already hashed
        //newUser.setRole(authRegisterDTO.getRole());

        return newUser;
    }

}
