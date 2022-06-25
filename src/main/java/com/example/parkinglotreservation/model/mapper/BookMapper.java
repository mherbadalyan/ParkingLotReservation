package com.example.parkinglotreservation.model.mapper;

import com.example.parkinglotreservation.model.dto.BookDto;
import com.example.parkinglotreservation.model.entity.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookMapper implements BaseMapper<Book, BookDto>{

    private final ParkingPlaceMapper parkingPlaceMapper;
    @Override
    public Book convertToEntity(BookDto dto) {
        Book book = new Book();
        book.setReserveDate(dto.getReserveDate());
        book.setCarNumber(dto.getCarNumber());
        book.setParkingPlace(parkingPlaceMapper.convertToEntity(dto.getParkingPlace()));
        return book;
    }

    @Override
    public BookDto convertToDto(Book entity) {
        return BookDto.builder().
                reserveDate(entity.getReserveDate()).
                id(entity.getId()).
                carNumber(entity.getCarNumber()).
                bookPrice(entity.getBookPrice()).
                parkingPlace(parkingPlaceMapper.convertToDto(entity.getParkingPlace())).
                build();
    }
}
