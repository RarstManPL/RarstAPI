package me.rarstman.rarstapi.configuration.annotation;

import me.rarstman.rarstapi.configuration.customparser.CustomParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ParseValue {

    Class<? extends CustomParser> value();

}
