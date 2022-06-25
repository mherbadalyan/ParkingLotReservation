package com.example.parkinglotreservation.repository;

import com.example.parkinglotreservation.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
