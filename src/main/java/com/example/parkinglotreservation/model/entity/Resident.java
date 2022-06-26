package com.example.parkinglotreservation.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String phone;

    @Column
    private String password;

    @Column
    private Double debt;
    @OneToMany(mappedBy = "resident")
    private List<Book> bookList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "resident_roles",
            joinColumns = @JoinColumn(name = "resident_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @Override
    public String toString() {
        return "Resident{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", account=" + debt +
                ", bookList=" + bookList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resident resident = (Resident) o;
        return Objects.equals(id, resident.id) && Objects.equals(name, resident.name) && Objects.equals(phone, resident.phone) && Objects.equals(password, resident.password) && Objects.equals(debt, resident.debt) && Objects.equals(bookList, resident.bookList) && Objects.equals(roles, resident.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone, password, debt, bookList, roles);
    }
}
