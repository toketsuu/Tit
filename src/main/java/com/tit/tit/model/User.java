package com.tit.tit.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@ToString
@RequiredArgsConstructor

public class User extends AbstractIdentity {
    private String email;
    private String password;
    private String name;
    private String description;
    private String photo;

    public User(String email, String password, String name, String description, String photo) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.description = description;
        this.photo = photo;
    }

    @OneToMany
    @ToString.Exclude
    List<User> friends;
}
