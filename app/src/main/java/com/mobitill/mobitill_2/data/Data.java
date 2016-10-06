package com.mobitill.mobitill_2.data;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by DI on 8/9/2016.
 */
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface Data {
}
