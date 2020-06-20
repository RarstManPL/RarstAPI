package me.rarstman.rarstapi.configuration;

import me.rarstman.rarstapi.RarstAPIPlugin;
import me.rarstman.rarstapi.command.CommandData;
import me.rarstman.rarstapi.configuration.annotation.ConfigName;
import me.rarstman.rarstapi.configuration.annotation.ParseValue;
import me.rarstman.rarstapi.item.ItemBuilder;
import me.rarstman.rarstapi.logger.Logger;
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

    private final Logger logger;

    public ConfigProvider(final File file, final InputStream defaultConfig) {
        this.file = file;
        this.defaultConfig = defaultConfig;
        this.yamlConfiguration = new YamlConfiguration();
        this.logger = RarstAPIPlugin.getAPI().getAPILogger();
    }

    public boolean save() {
        if(!this.checkFileAndLoad()) {
            this.logger.error("Cannot save configuration '" + this.file.getPath() + "' due to load error.");
            return false;
        }
        this.logger.info("Saved configuration '" + this.file.getPath() + "'.");
        return true;
    }

    public boolean reload() {
        if(!this.save()) {
            this.logger.error("Cannot reload configuration '" + this.file.getPath() + "' due to save error.");
            return false;
        }
        this.parse();
        this.logger.info("Reloaded configuration '" + this.file.getPath() + "'.");
        return true;
    }

    public ConfigProvider initialize() {
        if(this.checkFileAndLoad()) {
            this.parse();
        }
        this.logger.info("Initialized configuration '" + this.file.getPath() + "'.");
        return this;
    }

    private boolean parse() {
        if(!this.checkFileAndLoad()){
            return false;
        }
        final ParseValue parseValueClass = this.getClass().getAnnotation(ParseValue.class);

        Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> Modifier.isPublic(field.getModifiers()) && field.isAnnotationPresent(ConfigName.class))
                .forEach(field -> {
                    try {
                        final String configPath = field.getAnnotation(ConfigName.class).value();

                        if (!this.yamlConfiguration.isSet(configPath)) {
                            this.logger.error("Value '" + configPath + "' in configuration '" + this.file.getPath() + "' isn't set. Using default or last correctly parsed value...");
                            return;
                        }
                        final ParseValue parseValue = field.isAnnotationPresent(ParseValue.class) ? field.getAnnotation(ParseValue.class).parseType() != ParseValue.ParseType.DISABLE ? field.getAnnotation(ParseValue.class) : null : parseValueClass != null ? parseValueClass : null;

                        if (parseValue != null) {
                            switch (parseValue.parseType()) {
                                case MESSAGE: {
                                    if (!this.yamlConfiguration.isString(configPath)) {
                                        this.logger.error("Value '" + configPath + "' in configuration '" + this.file.getPath() + "' isn't string. Using default or correctly parsed value... ");
                                        return;
                                    }
                                    final String string = this.yamlConfiguration.getString(configPath);
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
                                    field.set(this, message);
                                    break;
                                }
                                case ITEMBUILDER: {
                                    if (!this.yamlConfiguration.isString(configPath)) {
                                        this.logger.error("Value '" + configPath + "' in configuration '" + this.file.getPath() + "' isn't string. Using default or correctly parsed value... ");
                                        return;
                                    }
                                    field.set(this, new ItemBuilder(this.yamlConfiguration.getString(configPath)));
                                    break;
                                }
                                case COMMANDDATA: {
                                    if(!this.yamlConfiguration.isString(configPath + ".Name")
                                            || !this.yamlConfiguration.isList(configPath + ".Aliases")
                                            || !this.yamlConfiguration.isString(configPath + ".Usage")
                                            || !this.yamlConfiguration.isString(configPath + ".Description")
                                            || !this.yamlConfiguration.isBoolean(configPath + ".Enabled")) {
                                        this.logger.error("Incomplete command configuration data in file '" + this.file.getPath() + "', path '" + configPath + "'. Using default or last correctly parsed value...");
                                        return;
                                    }
                                    field.set(this, new CommandData(
                                            this.yamlConfiguration.getString(configPath + ".Name"),
                                            this.yamlConfiguration.getStringList(configPath + ".Aliases"),
                                            this.yamlConfiguration.getString(configPath + ".Description"),
                                            this.yamlConfiguration.getString(configPath + ".Usage"),
                                            this.yamlConfiguration.getBoolean(configPath + ".Enabled")
                                    ));
                                    break;
                                }
                            }
                            return;
                        }
                        field.set(this, this.yamlConfiguration.get(configPath, field.get(this)));
                    } catch (final IllegalAccessException exception) {
                        this.logger.exception(exception, "Error while trying to set field '" + field.getName() + "' in configuration class '" + this.getClass().getName() + "'. Using default or last correctly parsed value...");
                    }
                });
        return true;
    }

    private boolean createBroken(){
        final String newName = "broken_" + this.file.getName() + "_" + System.currentTimeMillis() + ".yml";
        try {
            FileUtils.copyFile(this.file, new File(this.file.getParentFile(), newName));
            this.file.delete();
        } catch (final IOException exception) {
            this.logger.exception(exception, "Error while trying to create broken version of configuration '" + this.file.getPath() + "'. Using default or last correctly parsed configuration values...");
            return false;
        }
        this.logger.info("Created broken version of configuration '" + this.file.getPath() + "'.");
        return true;
    }

    private boolean checkFile() {
        if(!this.file.getParentFile().exists()) {
            try {
                this.file.getParentFile().mkdirs();
                this.logger.info("Created folder '" + this.file.getParentFile().getPath() + "'.");
            } catch (final SecurityException exception) {
                this.logger.exception(exception, "Error while trying to create folder '" + this.file.getParentFile().getPath() + "'. Using default or last correctly parsed configuration values...");
                return false;
            }
        }

        if(!this.file.exists()) {
            try {
                this.file.createNewFile();
                FileUtils.copyToFile(this.defaultConfig, file);
                this.logger.info("Created configuration file " + this.file.getPath() + ".");
            } catch (final IOException exception) {
                this.logger.exception(exception, "Error while trying to create configuration file '" + this.file.getPath() + "'. Using default or last correctly parsed configuration values...");
                return false;
            }
        }
        return true;
    }

    private boolean checkFileAndLoad() {
        if(!this.checkFile()){
            return false;
        }

        try {
            this.yamlConfiguration.load(this.file);
        } catch (final IOException exception) {
            this.logger.exception(exception, "Error while trying to load yamlConfiguration of '" + this.file.getPath() + "'. Using default or last correctly parsed configuration values...");
            return false;
        } catch (final ParserException | InvalidConfigurationException exception) {
            this.logger.error("Configuration '" + this.file.getPath() + "' has syntax error(s). Trying to fix...");
            if(!this.createBroken()) {
                return false;
            }
            return this.checkFileAndLoad();
        }
        return true;
    }

}
