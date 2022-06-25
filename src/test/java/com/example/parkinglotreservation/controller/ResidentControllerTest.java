package com.example.parkinglotreservation.controller;

import com.example.parkinglotreservation.model.dto.ResidentDto;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ResidentControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    ObjectMapper mapper;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void getResidentSuccessTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/parking-lot-reservation/resident/{phone}", "5555")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    public void updateResidentAccountSuccessTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/parking-lot-reservation/resident/{phone}/{money}", "5555",100)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    public void updateResidentFailureTest() throws Exception {

        ResidentDto dto = new ResidentDto();
        dto.setPassword(passwordEncoder.encode("1111"));
        dto.setPhone("101010");

        String userJson = mapper.writeValueAsString(dto);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/parking-lot-reservation/resident/{phone}", "555")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(userJson.getBytes()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    public void removeResidentSuccessTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/parking-lot-reservation/resident/{phone}", "5555")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
