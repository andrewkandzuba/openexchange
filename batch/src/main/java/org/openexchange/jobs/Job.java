package org.openexchange.jobs;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Job {
    String concurrencyString() default "";
}
