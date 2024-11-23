package com.gdgotp.d2d.infra.osrm.types;

import lombok.Getter;

import java.util.List;

@Getter
public class Maneuver {
    private int bearingAfter;
    private int bearingBefore;
    private List<Double> location;
    private String type;
    private String modifier;
}
