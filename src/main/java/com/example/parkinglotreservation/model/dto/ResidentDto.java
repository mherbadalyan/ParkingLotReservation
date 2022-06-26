package com.example.parkinglotreservation.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResidentDto {

    private Long id;

    private String name;

    private String phone;

    private Double debt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private List<BookDto> bookList = new ArrayList<>();
}
