package com.jesus.phisheye.repository;

import com.jesus.phisheye.entity.RegistrantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrantRepository extends JpaRepository<RegistrantEntity, Long> {
}
