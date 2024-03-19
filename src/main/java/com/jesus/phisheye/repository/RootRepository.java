package com.jesus.phisheye.repository;

import com.jesus.phisheye.entity.RootEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RootRepository extends JpaRepository<RootEntity, Long> {

    Set<RootEntity> findAllByOriginDns(String fullDns);
}
