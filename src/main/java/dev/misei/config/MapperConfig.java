package dev.misei.config;

import dev.misei.domain.mapper.appointment.AppointmentMapper;
import dev.misei.domain.mapper.appointment.SimpleAppointmentMapper;
import dev.misei.domain.mapper.business.BusinessMapper;
import dev.misei.domain.mapper.business.SimpleBusinessMapper;
import dev.misei.domain.mapper.user.SimpleUserMapper;
import dev.misei.domain.mapper.user.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public BusinessMapper organizationMapper() {
        return BusinessMapper.INSTANCE;
    }

    @Bean
    public UserMapper userMapper() {
        return UserMapper.INSTANCE;
    }

    @Bean
    public AppointmentMapper appointmentMapper() {
        return AppointmentMapper.INSTANCE;
    }

    @Bean
    public SimpleBusinessMapper simpleOrganizationMapper() {
        return SimpleBusinessMapper.INSTANCE;
    }

    @Bean
    public SimpleUserMapper simpleUserMapper() {
        return SimpleUserMapper.INSTANCE;
    }

    @Bean
    public SimpleAppointmentMapper simpleAppointmentMapper() {
        return SimpleAppointmentMapper.INSTANCE;
    }
}
