package com.example.parkinglotreservation.controller;

import com.example.parkinglotreservation.model.dto.BookDto;
import com.example.parkinglotreservation.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("parking-lot-reservation/book")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/{car-number}")
    private ResponseEntity<?> createBooking(@PathVariable("car-number") String carNumber) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        BookDto bookDto = bookService.createBooking(auth.getName(), carNumber);

        return new ResponseEntity<>(bookDto,
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> removeBooking(@PathVariable("id") Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        BookDto bookDto = bookService.completeBooking(auth.getName(), id);

        return new ResponseEntity<>(bookDto,
                HttpStatus.OK);
    }
}

