package com.tit.tit.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@MappedSuperclass
public abstract class AbstractIdentity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter

    Long id;
}
