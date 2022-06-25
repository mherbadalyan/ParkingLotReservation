package com.example.parkinglotreservation.model.entity;

import com.example.parkinglotreservation.model.enums.Status;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ParkingPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prk_plc_id_generator_id_generator")
    @SequenceGenerator(name = "prk_plc_id_generator", sequenceName = "prk_plc_id_generator_seq", allocationSize = 30)
    private Long id;

    @Column
    private Integer placeNumber;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ParkingPlace that = (ParkingPlace) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
