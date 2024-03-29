package com.jesus.phisheye.repository;

import com.jesus.phisheye.entity.RegistrarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrarRepository extends JpaRepository<RegistrarEntity, Long> {
}
