package com.gdgotp.d2d.location.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;
import java.util.UUID;

@Entity(name = "location")
@Getter
@Setter
public class LocationEntity {
    @Id
    private UUID id;

    @NotEmpty
    @Column(unique = true)
    private long nodeId;

    @NotEmpty
    @Column
    private String name;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @NotEmpty
    @Column
    private String type;

    @Column
    private Integer floor;

    @Column
    @ColumnDefault("0")
    private Integer level;

    @OneToMany(mappedBy = "location", fetch = FetchType.EAGER)
    private List<TagEntity> tag;
}
