package com.Zi.spring_IMS.controller;


import com.Zi.spring_IMS.model.dto.AuthLoginDTO;
import com.Zi.spring_IMS.model.dto.AuthRegisterDTO;
import com.Zi.spring_IMS.model.dto.AuthResponseDTO;
import com.Zi.spring_IMS.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
     @Autowired
     private MockMvc mockMvc;

     @MockBean
     private AuthService authService;

     private AuthLoginDTO authLoginDTO;
     private AuthRegisterDTO authRegisterDTO;
     private AuthResponseDTO authResponseDTO;

     @BeforeEach
     void setUp() {
          authLoginDTO = new AuthLoginDTO();
          authLoginDTO.setUsername("zzhuang@gmail.com");
          authLoginDTO.setPassword("@Zz123456");

          authRegisterDTO = new AuthRegisterDTO();
          authRegisterDTO.setName("newUser");
          authRegisterDTO.setUsername("newuser@gamil.com");
          authRegisterDTO.setPassword("@Nu123456");

          authResponseDTO = AuthResponseDTO.builder()
                  .token("zzToken")
                  .build();
     }

     @Test
     void testLoginAuthentication() throws Exception {
          // mock the expected behavior of AuthService layer
          when(authService.loginAuthentication(authLoginDTO)).thenReturn(authResponseDTO);

          // perform the POST request and validate the response
          mockMvc.perform(post("/auth/login")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content("{\"username\": \"zzhuang@gmail.com\", \"password\": \"@Zz123456\"}"))
                  .andExpect(status().isOk())
                  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                  .andExpect(jsonPath("$.token").value(authResponseDTO.getToken()));
     }

     @Test
     void testRegisterAuthentication() throws Exception {
          when(authService.registerAuthentication(authRegisterDTO)).thenReturn(authResponseDTO);

          mockMvc.perform(post("/auth/register")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content("{\"name\": \"newUser\", \"username\": \"newuser@gamil.com\", \"password\": \"@Nu123456\"}"))
                  .andExpect(status().isOk())
                  .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                  .andExpect(jsonPath("$.token").value(authResponseDTO.getToken()));
     }
}
