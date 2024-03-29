package com.jesus.phisheye.repository;

import com.jesus.phisheye.entity.NMDomainStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NMDomainStatusRepository extends JpaRepository<NMDomainStatusEntity, Long> {
}
