package com.example.parkinglotreservation.service;

import com.example.parkinglotreservation.model.dto.BookDto;
import com.example.parkinglotreservation.model.entity.Book;
import com.example.parkinglotreservation.model.entity.ParkingPlace;
import com.example.parkinglotreservation.model.entity.Resident;
import com.example.parkinglotreservation.model.enums.Status;
import com.example.parkinglotreservation.model.mapper.BookMapper;
import com.example.parkinglotreservation.repository.BookRepository;
import com.example.parkinglotreservation.repository.ParkingPlaceRepository;
import com.example.parkinglotreservation.repository.ResidentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final ResidentRepository residentRepository;

    private final ParkingPlaceRepository parkingPlaceRepository;

    private final BookMapper bookMapper;

    public Optional<BookDto> createBook(String name ,String carNumber) {

        Optional<Resident> residentFromData = residentRepository.findByName(name);

        Optional<ParkingPlace> parkingPlace = parkingPlaceRepository.findFirstByStatusAvailable();

        if (parkingPlace.isEmpty()) {
            return Optional.empty();
        }else {
            parkingPlace.get().setStatus(Status.RESERVED);
        }

        Book book = new Book();
        book.setReserveDate(LocalDate.now());
        book.setResident(residentFromData.get());
        book.setParkingPlace(parkingPlace.get());
        book.setCarNumber(carNumber);
        book.setBookPrice(0D);

        Book savedBook = bookRepository.save(book);

        return Optional.of(bookMapper.convertToDto(savedBook));
    }

    public Optional<BookDto> removeBook(String name,Long bookId) {

        Optional<Book> bookToRemove = bookRepository.findById(bookId);

        if (bookToRemove.isEmpty() || !bookToRemove.get().getResident().getName().equals(name)) {
            return Optional.empty();
        }

        Period period = Period.between(bookToRemove.get().getReserveDate(), LocalDate.now());

        int bookDays = period.getDays() + 1;
        Double bookPrice = bookDays * 500D;
        Double account = bookToRemove.get().getResident().getAccount();

        bookToRemove.get().getParkingPlace().setStatus(Status.AVAILABLE);
        bookToRemove.get().getResident().setAccount(account + bookPrice);

        bookRepository.delete(bookToRemove.get());

        bookToRemove.get().setBookPrice(bookPrice);
        return Optional.of(bookMapper.convertToDto(bookToRemove.get()));
    }
}
