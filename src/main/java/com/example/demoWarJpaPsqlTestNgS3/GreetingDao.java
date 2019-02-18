/**
 * 
 */
package com.example.demoWarJpaPsqlTestNgS3;

import java.util.List;

/**
 * @author x173117
 *
 */
public interface GreetingDao {

    List<GreetingEntity> findAll();

    GreetingEntity add(GreetingEntity entity);

    GreetingEntity update(GreetingEntity entity);

}
