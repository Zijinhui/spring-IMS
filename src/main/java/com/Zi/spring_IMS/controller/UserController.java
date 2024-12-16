package com.Zi.spring_IMS.controller;

import com.Zi.spring_IMS.model.dto.UserDTO;
import com.Zi.spring_IMS.model.entity.User;
import com.Zi.spring_IMS.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() { return ResponseEntity.ok(userService.getAllUsers()); }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) { return ResponseEntity.ok(userService.getUserById(id)); }

    @PutMapping // only allow Admin to edit name, password, role and status
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserDTO userDTO) { return ResponseEntity.ok(userService.updateUser(userDTO)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Integer id) { return ResponseEntity.ok(userService.deleteUser(id)); }
}
