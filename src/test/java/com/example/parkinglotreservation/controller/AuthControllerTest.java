package com.example.parkinglotreservation.controller;

import com.example.parkinglotreservation.model.dto.LoginDto;
import com.example.parkinglotreservation.model.dto.SignUpDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;


@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    ObjectMapper mapper;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @Transactional
    public void signInSuccessTest() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setPhone("1111");
        loginDto.setPassword("1111");

        String userJson = mapper.writeValueAsString(loginDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/sign-in")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(userJson.getBytes()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @Transactional
    public void signUpSuccessTest() throws Exception {

        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setRole("RESIDENT");
        signUpDto.setName("Vandam");
        signUpDto.setPhone("6666");
        signUpDto.setPassword(passwordEncoder.encode("1111"));


        String userJson = mapper.writeValueAsString(signUpDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/sign-up")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(userJson.getBytes()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    public void signUpFailureTest() throws Exception {

        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setRole("RESIDENT");
        signUpDto.setName("Vandam");
        signUpDto.setPhone("1111");
        signUpDto.setPassword(passwordEncoder.encode("1111"));


        String userJson = mapper.writeValueAsString(signUpDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/sign-up")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(userJson.getBytes()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
}
