package com.jesus.phisheye.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "registrant")
public class RegistrantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String street;
    private String city;
    private String province;
    @JsonProperty("postal_code")
    private String postalCode;
    private String country;
    private String phone;
    private String fax;
    private String email;

}
