package me.rarstman.rarstapi.configuration.customparser.impl;

import me.rarstman.rarstapi.configuration.ConfigProvider;
import me.rarstman.rarstapi.configuration.customparser.CustomParser;
import me.rarstman.rarstapi.configuration.customparser.exception.CustomParserException;
import me.rarstman.rarstapi.message.Message;
import me.rarstman.rarstapi.message.impl.ChatMessage;
import me.rarstman.rarstapi.message.impl.TitleMessage;
import org.bukkit.configuration.file.YamlConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

public class MessageParser implements CustomParser<Message> {

    @Override
    public Message parse(final Class<? extends ConfigProvider> configClass, final Field field, final YamlConfiguration yamlConfiguration, final String configPath) throws CustomParserException {
        final ParseMessageData parseMessageData = field.isAnnotationPresent(ParseMessageData.class) ? field.getAnnotation(ParseMessageData.class) : configClass.isAnnotationPresent(ParseMessageData.class) ? configClass.getAnnotation(ParseMessageData.class) : null;
        if(parseMessageData == null) {
            throw new CustomParserException("EXC");
        }

        if (!yamlConfiguration.isString(configPath)) {
            throw new CustomParserException("XD");
        }
        final String string = yamlConfiguration.getString(configPath);
        Message message;

        switch (parseMessageData.value()) {
            default:
            case CHAT: {
                message = new ChatMessage(string);
                break;
            }
            case TITLE: {
                message = new TitleMessage(string);
                break;
            }
        }
        return message;
    }

    @Target({ElementType.FIELD, ElementType.TYPE})
    @Retention(value = RetentionPolicy.RUNTIME)
    public @interface ParseMessageData {
        MessageType value() default MessageType.CHAT;
    }

    public enum MessageType {
        CHAT,
        TITLE;
    }

}
