package com.gdgotp.d2d.infra.osrm.types;

import lombok.Getter;

import java.util.List;

@Getter
public class Route {
    private Geometry geometry;
    private double weight;
    private double duration;
    private List<Leg> legs;
    private String weightName;
    private double distance;
}
