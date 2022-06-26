package com.example.parkinglotreservation.service;

import com.example.parkinglotreservation.exception.exception_classes.BookingNotFoundException;
import com.example.parkinglotreservation.exception.exception_classes.NoAvailableParkingPlaceException;
import com.example.parkinglotreservation.exception.exception_classes.ResidentNotFoundException;
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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public BookDto createBooking(String name, String carNumber) {

        Optional<Resident> residentFromData = residentRepository.findByName(name);

        if (residentFromData.isEmpty()) {
            throw new ResidentNotFoundException(name);
        }

        Optional<ParkingPlace> parkingPlace = parkingPlaceRepository.findFirstByStatusAvailable();

        if (parkingPlace.isEmpty()) {
            throw new NoAvailableParkingPlaceException();
        } else {
            parkingPlace.get().setStatus(Status.RESERVED);
        }

        Book book = new Book();
        book.setReserveDate(LocalDate.now());
        book.setResident(residentFromData.get());
        book.setParkingPlace(parkingPlace.get());
        book.setCarNumber(carNumber);
        book.setBookPrice(0D);

        Book savedBook = bookRepository.save(book);

        return bookMapper.convertToDto(savedBook);
    }

    @Transactional
    public BookDto completeBooking(String name, Long bookId) {

        Optional<Book> bookToRemove = bookRepository.findById(bookId);

        if (bookToRemove.isEmpty()) {
            throw new BookingNotFoundException("There is no booking with this id " + bookId);
        }

        if (!bookToRemove.get().getResident().getName().equals(name)) {
            throw new BookingNotFoundException("You have not booking with this id " + bookId);
        }

        Period period = Period.between(bookToRemove.get().getReserveDate(), LocalDate.now());

        int bookDays = period.getDays() + 1;
        Double bookPrice = bookDays * 500D;
        Double debt = bookToRemove.get().getResident().getDebt();

        bookToRemove.get().getParkingPlace().setStatus(Status.AVAILABLE);
        bookToRemove.get().getResident().setDebt(debt + bookPrice);

        bookRepository.delete(bookToRemove.get());

        bookToRemove.get().setBookPrice(bookPrice);
        return bookMapper.convertToDto(bookToRemove.get());
    }
}
