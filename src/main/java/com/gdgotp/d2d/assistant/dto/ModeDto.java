package com.gdgotp.d2d.assistant.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class ModeDto {
     public enum MODE {
        ROUTE("route"),
        MARKER("marker"),
        NONE("none");

        private String value;

        MODE(String mode) {
            this.value = mode;
        }
    }

    private MODE mode;
    private String output;

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert to JSON", e);
        }
    }
}
