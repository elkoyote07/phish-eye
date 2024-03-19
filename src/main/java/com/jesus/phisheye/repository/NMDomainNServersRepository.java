package com.jesus.phisheye.repository;

import com.jesus.phisheye.entity.NMDomainNServersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NMDomainNServersRepository extends JpaRepository<NMDomainNServersEntity, Long> {
}
