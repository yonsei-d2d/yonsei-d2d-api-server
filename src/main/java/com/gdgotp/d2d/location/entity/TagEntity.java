package com.gdgotp.d2d.location.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "tag")
@Getter
@Setter
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Column
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    private LocationEntity location;
}