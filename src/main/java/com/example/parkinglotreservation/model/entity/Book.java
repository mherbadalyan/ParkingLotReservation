package com.example.parkinglotreservation.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDate reserveDate;

    @Column
    private String carNumber;

    @Column
    private Double bookPrice;

    @ManyToOne
    @JoinColumn(name = "resident_id")
    private Resident resident;

    @ManyToOne
    @JoinColumn(name = "prk_plc_id")
    private ParkingPlace parkingPlace;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", reserveDate=" + reserveDate +
                ", parkingPlace=" + parkingPlace +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(reserveDate, book.reserveDate) && Objects.equals(carNumber, book.carNumber) && Objects.equals(bookPrice, book.bookPrice) && Objects.equals(resident, book.resident) && Objects.equals(parkingPlace, book.parkingPlace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reserveDate, carNumber, bookPrice, resident, parkingPlace);
    }
}
