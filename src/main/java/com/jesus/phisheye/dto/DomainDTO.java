package com.jesus.phisheye.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DomainDTO {

    private String id;
    private String domain;
    private String punycode;
    private String name;
    private String extension;
    @JsonProperty("whois_server")
    private String whoisServer;
    @JsonProperty("status")
    private List<String> statusList;
    @JsonProperty("name_servers")
    private List<String> nameServerslist;
    @JsonProperty("created_date")
    private Date createdDate;
    @JsonProperty("created_date_in_time")
    private Date createdDateInTime;
    @JsonProperty("updated_date")
    private Date updatedDate;
    @JsonProperty("updated_date_in_time")
    private Date updatedDateInTime;
    @JsonProperty("expiration_date")
    private Date expirationDate;
    @JsonProperty("expiration_date_in_time")
    private Date expirationDateInTime;

}
