package com.jesus.phisheye.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "root")
public class RootEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "owned", nullable = false)
    private Boolean owned;

    @Column(name = "is_info", nullable = false)
    private Boolean isInfo;

    @Column(name = "dns", nullable = false)
    private String dns;

    @Column(name = "origin_dns", nullable = false)
    private String originDns;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domain_id")
    private DomainEntity domainEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registrant_id")
    private RegistrantEntity registrantEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registrar_id")
    private RegistrarEntity registrarEntity;
}
