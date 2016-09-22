package org.openexchange.jobs;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Service
@interface Job {
    String runMethod() default "";

    int concurrency() default -1;

    String concurrencyString() default "";
}
