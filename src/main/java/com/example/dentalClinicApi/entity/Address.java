package com.example.dentalClinicApi.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "locality", nullable = false)
    private String locality;

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "province", nullable = false)
    private String province;

    public Address(String street, String locality, int number, String province) {
        this.street = street;
        this.locality = locality;
        this.number = number;
        this.province = province;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Address address = (Address) o;
        return id != null && Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
