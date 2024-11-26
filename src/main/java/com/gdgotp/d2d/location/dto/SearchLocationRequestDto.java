package com.gdgotp.d2d.location.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchLocationRequestDto {
    @NotEmpty
    private String query;
}
