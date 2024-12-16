package com.Zi.spring_IMS.controller;

import com.Zi.spring_IMS.model.dto.UserDTO;
import com.Zi.spring_IMS.model.entity.Account_Status;
import com.Zi.spring_IMS.model.entity.Role;
import com.Zi.spring_IMS.model.entity.User;
import com.Zi.spring_IMS.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//@SpringBootTest
//@AutoConfigureMockMvc
//public class UserControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//
//    private UserDTO userDTO;
//    private User user;
//    private List<User> userList;
//
//    @BeforeEach
//    void setUp() {
//        userDTO = UserDTO.builder()
//                .name("Zi Zhuang")
//                .username("zzhuang@gmail.com")
//                .password("@Zz1234567")
//                .role(Role.STAFF)
//                .status(Account_Status.ACTIVE)
//                .build();
//
//        user = new User();
//        user.setId(1);
//        user.setName("Zi Zhuang");
//        user.setUsername("zzhuang@gmail.com");
//        user.setPassword("@Zz1234567");
//        user.setRole(Role.STAFF);
//        user.setCreated_at(LocalDateTime.now());
//        user.setUpdate_at(LocalDateTime.now());
//        user.setStatus(Account_Status.ACTIVE);
//
//        userList = List.of(user);
//    }
//
//    @Test
//    @WithMockUser(authorities = {"ADMIN"}) // mock an admin user
//    void testGetAllUsers() throws Exception {
//        when(userService.getAllUsers()).thenReturn(userList);
//
//        mockMvc.perform(get("/users"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[0].name").value(user.getName()))
//                .andExpect(jsonPath("$[0].username").value(user.getUsername()))
//                .andExpect(jsonPath("$[0].password").value(user.getPassword()))
//                .andExpect(jsonPath("$[0].role").value(user.getRole().toString()))
//                .andExpect(jsonPath("$[0].status").value(user.getStatus().toString()));
//    }
//
//    @Test
//    @WithMockUser(authorities = {"ADMIN"}) // mock an admin user
//    void testGetUserById() throws Exception {
//        when(userService.getUserById(1)).thenReturn(user);
//
//        mockMvc.perform(get("/users/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(user.getId()))
//                .andExpect(jsonPath("$.name").value(user.getName()))
//                .andExpect(jsonPath("$.username").value(user.getUsername()))
//                .andExpect(jsonPath("$.password").value(user.getPassword()))
//                .andExpect(jsonPath("$.role").value(user.getRole().toString()))
//                .andExpect(jsonPath("$.status").value(user.getStatus().toString()));
//    }
//
//    @Test
//    @WithMockUser(authorities = {"ADMIN"}) // mock an admin user
//    void testUpdateUser() throws Exception {
//        // create a new UserDTO with updated values
//        UserDTO updatedUserDTO = UserDTO.builder()
//                .name("Zi Updated")
//                .username("zzhuang@gmail.com") // username should remain the same
//                .password("NewPassword@123")
//                .role(Role.MANAGER)
//                .status(Account_Status.ACTIVE)
//                .build();
//
//        when(userService.updateUser(updatedUserDTO)).thenReturn(user);
//
//        // use ObjectMapper to serialize UserDTO into JSON
//        String userJson = new ObjectMapper().writeValueAsString(updatedUserDTO);
//
//        mockMvc.perform(put("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(userJson)) // pass the serialized JSON payload
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id").value(user.getId()))
//                .andExpect(jsonPath("$.name").value(user.getName()))
//                .andExpect(jsonPath("$.username").value(user.getUsername()))// username should remain the same
//                .andExpect(jsonPath("$.password").value(user.getPassword()))
//                .andExpect(jsonPath("$.role").value(user.getRole().toString()))
//                .andExpect(jsonPath("$.status").value(user.getStatus().toString()));
//    }
//
//    @Test
//    @WithMockUser(authorities = {"ADMIN", "MANAGER"}) // Mock an admin/manager user
//    void testDeleteUser() throws Exception {
//        when(userService.deleteUser(1)).thenReturn(true);
//
//        mockMvc.perform(delete("/users/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("true"));
//    }
//
//}
