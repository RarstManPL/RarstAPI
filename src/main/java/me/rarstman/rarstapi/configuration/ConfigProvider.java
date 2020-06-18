package me.rarstman.rarstapi.configuration;

import me.rarstman.rarstapi.command.CommandData;
import me.rarstman.rarstapi.configuration.annotation.ConfigName;
import me.rarstman.rarstapi.configuration.annotation.ConfigPath;
import me.rarstman.rarstapi.configuration.annotation.ParseValue;
import me.rarstman.rarstapi.configuration.impl.RarstAPIConfig;
import me.rarstman.rarstapi.item.ItemBuilder;
import me.rarstman.rarstapi.message.Message;
import me.rarstman.rarstapi.message.impl.ActionBarMessage;
import me.rarstman.rarstapi.message.impl.BossBarMessage;
import me.rarstman.rarstapi.message.impl.ChatMessage;
import me.rarstman.rarstapi.message.impl.TitleMessage;
import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.parser.ParserException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public abstract class ConfigProvider {

    private final File file;
    private final InputStream defaultConfig;
    private YamlConfiguration yamlConfiguration;

    public ConfigProvider(final File file, final InputStream defaultConfig) {
        this.file = file;
        this.defaultConfig = defaultConfig;
        this.yamlConfiguration = new YamlConfiguration();
    }

    public boolean save() {
        try {
            this.yamlConfiguration.load(this.file);
        } catch (final IOException exception) {
            return this.checkFileAndLoad();
        } catch (final ParserException | InvalidConfigurationException exception) {
            this.createBroken();
            return this.checkFileAndLoad();
        }
        return true;
    }

    public boolean reload() {
        if(!this.save()) {
            return false;
        }
        this.parse();
        return true;
    }

    public ConfigProvider initialize() {
        if(!this.load()) {
            return this;
        }
        this.parse();
        return this;
    }

    private void parse() {
        if(!this.checkFileAndLoad()){
            return;
        }
        Arrays.stream(this.getClass().getFields())
                .filter(field -> Modifier.isPublic(field.getModifiers()) && field.isAnnotationPresent(ConfigName.class))
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        final String configPath = (field.isAnnotationPresent(ConfigPath.class) ? field.getAnnotation(ConfigPath.class).value() + "." : "") + field.getAnnotation(ConfigName.class).value();

                        if (!this.yamlConfiguration.isSet(configPath)) {
                            return;
                        }

                        if (field.isAnnotationPresent(ParseValue.class)) {
                            if (!this.yamlConfiguration.isString(configPath)) {
                                return;
                            }
                            final String string = this.yamlConfiguration.getString(configPath);
                            final ParseValue parseValue = field.getAnnotation(ParseValue.class);

                            switch (parseValue.parseType()) {
                                case MESSAGE: {
                                    Message message;

                                    switch (parseValue.messageType()) {
                                        default:
                                        case CHAT: {
                                            message = new ChatMessage(string);
                                            break;
                                        }
                                        case TITLE: {
                                            message = new TitleMessage(string);
                                            break;
                                        }
                                        case ACTIONBAR: {
                                            message = new ActionBarMessage(string);
                                            break;
                                        }
                                        case BOSSBAR: {
                                            message = new BossBarMessage(string);
                                            break;
                                        }

                                    }
                                    field.set(null, message);
                                    break;
                                }
                                case ITEMBUILDER: {
                                    field.set(null, new ItemBuilder(string));
                                    break;
                                }
                                case COMMANDDATA: {
                                    if(!this.yamlConfiguration.isString(configPath + ".Name")
                                            || !this.yamlConfiguration.isList(configPath + ".Aliases")
                                            || !this.yamlConfiguration.isString("Usage")
                                            || !this.yamlConfiguration.isString("Description")) {
                                        return;
                                    }
                                    field.set(null, new CommandData(
                                            this.yamlConfiguration.getString(configPath + ".Name"),
                                            this.yamlConfiguration.getStringList(configPath + ".Aliases"),
                                            this.yamlConfiguration.getString(configPath + ".Description"),
                                            this.yamlConfiguration.getString(configPath + ".Usage")
                                    ));
                                    break;
                                }
                            }
                            return;
                        }
                        field.set(null, this.yamlConfiguration.get(configPath, field.get(null)));
                    } catch (final IllegalAccessException exception) {}
                });
    }

    private boolean load() {
        try {
            this.yamlConfiguration.load(this.file);
        } catch (final IOException exception) {
            return this.checkFileAndLoad();
        } catch (final ParserException | InvalidConfigurationException exception) {
            this.createBroken();
            return this.checkFileAndLoad();
        }
        return true;
    }

    private void createBroken(){
        final String newName = "broken_" + this.file.getName() + "_" + System.currentTimeMillis() + ".yml";
        try {
            FileUtils.copyFile(this.file, new File(this.file.getParentFile(), newName));
        } catch (final IOException ignored) {}
        this.file.delete();
    }

    private boolean checkFileAndLoad() {
        if(!this.file.exists()) {
            try {
                this.file.createNewFile();
                FileUtils.copyToFile(this.defaultConfig, file);
            } catch (final IOException exception) {
                return false;
            }
        }
        return this.load();
    }

}
