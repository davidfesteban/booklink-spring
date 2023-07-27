package dev.misei.domain;


import java.time.LocalTime;

public record WorkingHours(LocalTime workStartHour, LocalTime workStopHour, LocalTime breakStartHour,
                           LocalTime breakStopHour) {

    /*public static final ObjectMapper objectMapper = new ObjectMapper();

    public static WorkingHours fromJsonString(String jsonString) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, WorkingHours.class);
    }

    @Override
    public String toString() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }*/
}
