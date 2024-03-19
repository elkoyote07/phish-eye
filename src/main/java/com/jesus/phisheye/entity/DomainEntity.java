package com.jesus.phisheye.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "domain")
public class DomainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "domain_name")
    private String domainName;

    private String punycode;

    private String name;

    private String extension;

    @Column(name = "whois_server")
    private String whoisServer;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "created_date_in_time")
    private Date createdDateInTime;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "updated_date_in_time")
    private Date updatedDateInTime;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "expiration_date_in_time")
    private Date expirationDateInTime;

}
