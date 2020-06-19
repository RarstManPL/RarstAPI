package me.rarstman.rarstapi.configuration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ParseValue {

    ParseType parseType();
    MessageType messageType() default MessageType.CHAT;

    enum ParseType {
        MESSAGE,
        ITEMBUILDER,
        COMMANDDATA,
        DISABLE;
    }

    enum MessageType {
        ACTIONBAR,
        TITLE,
        BOSSBAR,
        CHAT;
    }

}
