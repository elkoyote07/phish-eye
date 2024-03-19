package com.jesus.phisheye.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrantDTO {

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
