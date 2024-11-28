package com.gdgotp.d2d.assistant.dto;

import com.gdgotp.d2d.location.dto.LocationResponseDto;
import com.gdgotp.d2d.route.dto.RouteResponseDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AssistantResponseDto {
    private String answer;
    private RouteResponseDto route;
    private LocationResponseDto location;
}
