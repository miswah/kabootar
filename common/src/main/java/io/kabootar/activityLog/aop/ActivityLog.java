package io.kabootar.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityLog {
    String action() default "";
    String module() default "";
    String target() default "";
    boolean captureParams() default true;
    String extra() default  "";
}
