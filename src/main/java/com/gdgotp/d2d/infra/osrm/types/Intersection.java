package com.gdgotp.d2d.infra.osrm.types;

import lombok.Getter;

import java.util.List;

@Getter
public class Intersection {
    private int out;
    private Integer in;
    private List<Boolean> entry;
    private List<Integer> bearings;
    private List<Double> location;
}
