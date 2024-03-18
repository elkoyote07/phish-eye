package com.jesus.phisheye.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrarDTO {

    private String name;
    private String phone;
    private String email;
    @JsonProperty("referral_url")
    private String referralUrl;

}
