package com.gdgotp.d2d.infra.osrm;

import com.gdgotp.d2d.infra.osrm.types.OsrmRouteResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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
        return requestRoute(String.format("%s%f,%f;%f,%f?annotations=true&alternatives=false&steps=true&exclude=indoor",
                this.endpoint, startLng, startLat, destLng, destLat));
    }
}
