package dev.misei.domain.entity;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalTime;

public record WorkingHours(LocalTime workStartHour, LocalTime workStopHour, LocalTime breakStartHour,
                           LocalTime breakStopHour) {

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static WorkingHours fromJsonString(String jsonString) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, WorkingHours.class);
    }

    public String toJsonString() throws JsonProcessingException {
        return objectMapper.writeValueAsString(this);
    }
}
