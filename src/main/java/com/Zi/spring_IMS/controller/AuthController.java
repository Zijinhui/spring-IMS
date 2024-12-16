package com.Zi.spring_IMS.controller;

import com.Zi.spring_IMS.model.dto.AuthLoginDTO;
import com.Zi.spring_IMS.model.dto.AuthRegisterDTO;
import com.Zi.spring_IMS.model.dto.AuthResponseDTO;
import com.Zi.spring_IMS.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login") // need to add validation
    public ResponseEntity<AuthResponseDTO> loginAuthentication(@RequestBody AuthLoginDTO authLoginDTO) {
        return ResponseEntity.ok(authService.loginAuthentication(authLoginDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registerAuthentication(@Valid @RequestBody AuthRegisterDTO authRegisterDTO) {
        //check validation in this layer
        return ResponseEntity.ok(authService.registerAuthentication(authRegisterDTO));
    }
}

// http://localhost:35729/livereload.js