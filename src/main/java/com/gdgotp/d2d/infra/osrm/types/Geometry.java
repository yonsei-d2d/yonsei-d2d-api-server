package com.gdgotp.d2d.infra.osrm.types;

import lombok.Getter;

import java.util.List;

@Getter
public class Geometry {
    private String type;
    private List<List<Double>> coordinates;
}
