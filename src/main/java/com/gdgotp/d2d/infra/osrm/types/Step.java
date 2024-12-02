package com.gdgotp.d2d.infra.osrm.types;

import lombok.Getter;

import java.util.List;

@Getter
public class Step {
    private Geometry geometry;
    private Maneuver maneuver;
    private String mode;
    private String name;
    private List<Intersection> intersections;
    private String drivingSide;
    private double weight;
    private double duration;
    private double distance;
}
