package com.example.parkinglotreservation.model.mapper;

import com.example.parkinglotreservation.model.dto.BookDto;
import com.example.parkinglotreservation.model.dto.ResidentDto;
import com.example.parkinglotreservation.model.entity.Resident;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ResidentMapper implements BaseMapper<Resident, ResidentDto>{

    private final BookMapper bookMapper;
    @Override
    public Resident convertToEntity(ResidentDto dto) {
        Resident resident = new Resident();
        resident.setName(dto.getName());
        resident.setPassword(dto.getPassword());
        resident.setPhone(dto.getPhone());
        resident.setDebt(dto.getDebt());
        return resident;
    }

    @Override
    public ResidentDto convertToDto(Resident entity) {
        return ResidentDto.builder().
                id(entity.getId()).
                name(entity.getName()).
                phone(entity.getPhone()).
                debt(entity.getDebt()).
                bookList((List<BookDto>) bookMapper.convertToDtos(entity.getBookList())).
                build();
    }
}
