package com.example.dentalClinicApi.entity.auth;

import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@ToString
@Entity
@Table(name = "users", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"userName"}),
                @UniqueConstraint(columnNames = {"email"})
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String userName;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "userRoles",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id")
    )
    private Set<Role> roleUser;
}
