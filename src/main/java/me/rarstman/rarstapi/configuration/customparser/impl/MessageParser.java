package me.rarstman.rarstapi.configuration.customparser.impl;

import me.rarstman.rarstapi.configuration.ConfigProvider;
import me.rarstman.rarstapi.configuration.customparser.CustomParser;
import me.rarstman.rarstapi.configuration.customparser.exception.CustomParserException;
import me.rarstman.rarstapi.message.Message;
import me.rarstman.rarstapi.message.impl.ChatMessage;
import me.rarstman.rarstapi.message.impl.TitleMessage;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

public class MessageParser extends CustomParser<Message> {

    public MessageParser(final Class<? extends ConfigProvider> configClass, final Field field, final File configFile, final YamlConfiguration yamlConfiguration, final String configPath) {
        super(configClass, field, configFile, yamlConfiguration, configPath);
    }

    @Override
    public Message parse() throws CustomParserException {
        final ParseMessageData parseMessageData = this.field.isAnnotationPresent(ParseMessageData.class) ? this.field.getAnnotation(ParseMessageData.class) : this.configClass.isAnnotationPresent(ParseMessageData.class) ? this.configClass.getAnnotation(ParseMessageData.class) : null;
        if(parseMessageData == null) {
            throw new CustomParserException("Value '" + this.configPath + "' in configuration '" + this.configFile.getPath() + "' cannot be parsed due to lack of message type definition. Using default or last correctly parsed value...");
        }

        if (!this.yamlConfiguration.isString(this.configPath)) {
            throw new CustomParserException("Value '" + this.configPath + "' in configuration '" + this.configFile.getPath() + "' isn't string. Using default or last correctly parsed value... ");
        }
        final String string = this.yamlConfiguration.getString(this.configPath);
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
