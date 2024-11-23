package com.gdgotp.d2d.infra.osrm.types;

import lombok.Getter;

import java.util.List;

@Getter
public class Leg {
    private Annotation annotation;
    private String summary;
    private double weight;
    private double duration;
    private List<Step> steps;
    private double distance;
}
