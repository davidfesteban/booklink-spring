package dev.misei.domain.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import dev.misei.domain.entity.WorkingHours;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.stream.Collectors;

import static dev.misei.domain.entity.WorkingHours.objectMapper;

@Converter
public class DayOfWeekAndWorkingHoursConverter implements AttributeConverter<Map<DayOfWeek, WorkingHours>, String> {

    @Override
    public String convertToDatabaseColumn(Map<DayOfWeek, WorkingHours> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<DayOfWeek, WorkingHours> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<Map<DayOfWeek, WorkingHours>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

