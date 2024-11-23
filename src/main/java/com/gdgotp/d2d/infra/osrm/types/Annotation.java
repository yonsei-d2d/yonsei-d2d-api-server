package com.gdgotp.d2d.infra.osrm.types;

import lombok.Getter;

import java.util.List;


@Getter
public class Annotation {
    private List<Double> speed;
    private List<Double> weight;
    private List<Double> duration;
    private List<Integer> datasources;
    private List<Double> distance;
    private List<Long> nodes;
    private Metadata metadata;
}
