package com.jesus.phisheye.repository;

import com.jesus.phisheye.entity.RootEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomRootRepositoryImpl implements CustomRootRepository{

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void deleteByDns(String originDns) {
        String selectQuery = "SELECT r FROM RootEntity r WHERE r.dns = :dnsValue";
        List<RootEntity> rootEntityList = entityManager
                .createQuery(selectQuery, RootEntity.class)
                .setParameter("dnsValue", originDns)
                .getResultList();

        rootEntityList.forEach(entityManager::remove);
    }

}
