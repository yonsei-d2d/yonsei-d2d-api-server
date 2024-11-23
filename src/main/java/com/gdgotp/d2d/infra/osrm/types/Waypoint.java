package com.gdgotp.d2d.infra.osrm.types;


import lombok.Getter;

import java.util.List;

@Getter
public class Waypoint {
    private String name;
    private String hint;
    private double distance;
    private List<Double> location;
}
