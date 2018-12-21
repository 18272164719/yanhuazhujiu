package com.test.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ProjectCodeFlag {
    String name() default "平台代码参数";
}
