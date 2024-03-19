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
public class RootDTO {
    // https://whoisjsonapi.com/dashboard
    @JsonProperty("domain")
    private DomainDTO domain;
    @JsonProperty("registrar")
    private RegistrarDTO registrar;
    @JsonProperty("registrant")
    private RegistrantDTO registrant;
    private Boolean owned;
    private Boolean isInfo;
    private String dns;
    private String originDns;

}
