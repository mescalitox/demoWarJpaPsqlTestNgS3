/**
 * 
 */
package com.example.demoWarJpaPsqlTestNgS3;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 * @author x173117
 *
 */
@Repository
public class GreetingDaoImpl implements GreetingDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GreetingEntity> findAll() {

        String hq = "select greeting from GreetingEntity greeting";
        return entityManager.createQuery(hq, GreetingEntity.class).getResultList();
    }

    @Override
    public GreetingEntity add(GreetingEntity entity) {

        entityManager.persist(entity);
        return entity;
    }

    @Override
    public GreetingEntity update(GreetingEntity entity) {
        entityManager.merge(entity);
        return entity;
    }

}
