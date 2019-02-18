package com.example.demoWarJpaPsqlTestNgS3.aop;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface ExecutionTime.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ METHOD })
public @interface ExecutionTime {

}
