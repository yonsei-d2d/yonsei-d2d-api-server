package com.gdgotp.d2d.infra.osrm;

import com.gdgotp.d2d.common.types.Routable;
import com.gdgotp.d2d.infra.osrm.types.OsrmRouteResult;
import com.gdgotp.d2d.infra.osrm.types.Route;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class OsrmClient {

    @Value("${osrm.endpoint}")
    private String endpoint;

    private OsrmRouteResult requestRoute(String url) {
        WebClient webClient = WebClient.builder().build();
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(OsrmRouteResult.class)
                .block();
    }


    public OsrmRouteResult route(Double startLat, Double startLng, Double destLat, Double destLng) {
        return requestRoute(String.format("%s%f,%f;%f,%f?annotations=true&alternatives=false&steps=true&exclude=indoor&geometries=geojson",
                this.endpoint, startLng, startLat, destLng, destLat));
    }

    public OsrmRouteResult routeV2(Routable origin, Routable destination, List<Routable> waypoints) {
        String latlngList = String.valueOf(origin.getLng()) + "," + String.valueOf(origin.getLat()) + ";";
        for (Routable waypoint : waypoints) {
            latlngList = latlngList.concat(waypoint.getLng() + "," + waypoint.getLat() + ";");
        }
        latlngList = latlngList.concat(destination.getLng() + "," + destination.getLat());
        return requestRoute(String.format("%s%s?annotations=true&alternatives=false&steps=true&exclude=indoor&geometries=geojson",
                this.endpoint, latlngList));
    }
}
