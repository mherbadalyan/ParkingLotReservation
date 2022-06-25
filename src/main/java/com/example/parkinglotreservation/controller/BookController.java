package com.example.parkinglotreservation.controller;

import com.example.parkinglotreservation.model.dto.BookDto;
import com.example.parkinglotreservation.service.BookService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("parking-lot-reservation/book")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    @PostMapping("/{car-number}")
    private ResponseEntity<?> createBook(@PathVariable("car-number") String carNumber) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<BookDto> bookDto  = bookService.createBook(auth.getName() , carNumber);

        if (bookDto.isEmpty()) {
            logger.warn("There is problem with booking parking places with resident " + auth.getName());
            return new ResponseEntity<>("There is problem with booking parking places",
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(bookDto.get(),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> removeBook(@PathVariable("id") Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<BookDto> bookDto  = bookService.removeBook(auth.getName(),id);

        if (bookDto.isEmpty()) {
            logger.warn(auth.getName() + " resident have not book of parking place with this id " + id);
            return new ResponseEntity<>("You have not book of parking place with this id",
                    HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(bookDto.get(),
                HttpStatus.OK);
    }

}

