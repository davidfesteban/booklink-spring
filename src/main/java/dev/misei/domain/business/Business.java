package dev.misei.domain.business;

import org.springframework.data.annotation.Id;
import org.springframework.lang.Nullable;

import java.util.UUID;

public class Business {

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
