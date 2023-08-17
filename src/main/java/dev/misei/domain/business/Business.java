package dev.misei.domain.business;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Getter
public class Business extends AbstractAggregateRoot<Business> {
    @Id
    private UUID id;
    private String subdomain;
    private String name;
    private String address;
    private String phone;
    @Nullable
    private String email;
    OpeningHours openingHours;
    //Empty
    DeviatingHours deviatingHours;
}
