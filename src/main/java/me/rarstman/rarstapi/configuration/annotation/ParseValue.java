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
    DatabaseType databaseType() default DatabaseType.MYSQL;

    enum ParseType {
        MESSAGE,
        ITEMBUILDER,
        COMMANDDATA,
        DATABASEDATA,
        DISABLE;
    }

    enum MessageType {
        TITLE,
        CHAT;
    }

    enum DatabaseType {
        MYSQL;
    }

}
