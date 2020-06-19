package me.rarstman.rarstapi.configuration;

import me.rarstman.rarstapi.RarstAPIPlugin;
import me.rarstman.rarstapi.command.CommandData;
import me.rarstman.rarstapi.configuration.annotation.ConfigName;
import me.rarstman.rarstapi.configuration.annotation.ConfigPath;
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
            this.logger.error("Cannot save configuration due to load error");
            return false;
        }
        this.logger.info("Saved configuration " + this.file.getAbsolutePath() + ".");
        return true;
    }

    public boolean reload() {
        if(!this.save()) {
            this.logger.error("Cannot reload configuration due to save error.");
            return false;
        }
        this.parse();
        this.logger.info("Reloaded configuration " + this.file.getAbsolutePath() + ".");
        return true;
    }

    public ConfigProvider initialize() {
        if(!this.checkFileAndLoad()) {
            return this;
        }
        this.parse();
        this.logger.info("Initialized configuration " + this.file.getAbsolutePath() + ".");
        return this;
    }

    private boolean parse() {
        if(!this.checkFileAndLoad()){
            return false;
        }
        Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> Modifier.isPublic(field.getModifiers()) && field.isAnnotationPresent(ConfigName.class))
                .forEach(field -> {
                    try {
                        final String configPath = (field.isAnnotationPresent(ConfigPath.class) ? field.getAnnotation(ConfigPath.class).value() + "." : "") + field.getAnnotation(ConfigName.class).value();

                        if (!this.yamlConfiguration.isSet(configPath)) {
                            this.logger.error("Value " + configPath + " in configuration " + this.file.getAbsolutePath() + " isn't set. Using default or last correctly parsed value...");
                            return;
                        }

                        if (field.isAnnotationPresent(ParseValue.class)) {
                            if (!this.yamlConfiguration.isString(configPath)) {
                                this.logger.error("Value " + configPath + " in configuration " + this.file.getAbsolutePath() + " isn't string. Using default or correctly parsed value... ");
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
                                    field.set(this, message);
                                    break;
                                }
                                case ITEMBUILDER: {
                                    field.set(this, new ItemBuilder(string));
                                    break;
                                }
                                case COMMANDDATA: {
                                    if(!this.yamlConfiguration.isString(configPath + ".Name")
                                            || !this.yamlConfiguration.isList(configPath + ".Aliases")
                                            || !this.yamlConfiguration.isString("Usage")
                                            || !this.yamlConfiguration.isString("Description")) {
                                        this.logger.error("Incomplete command configuration data in file " + this.file.getAbsolutePath() + ", path " + configPath + ". Using default or last correctly parsed value...");
                                        return;
                                    }
                                    field.set(this, new CommandData(
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
                        field.set(this, this.yamlConfiguration.get(configPath, field.get(this)));
                    } catch (final IllegalAccessException exception) {
                        this.logger.exception(exception, "Error while trying to set field " + field.getName() + " in configuration class " + this.getClass().getName() + ". Using default or last correctly parsed value...");
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
            this.logger.exception(exception, "Error while trying to create broken version of configuration " + this.file.getAbsolutePath() + ". Using default or last correctly parsed configuration values...");
            return false;
        }
        this.logger.info("Created broken version of configuration " + this.file.getAbsolutePath() + ".");
        return true;
    }

    private boolean checkFile() {
        if(!this.file.exists()) {
            try {
                this.file.mkdirs();
                FileUtils.copyToFile(this.defaultConfig, file);
                this.logger.info("Created configuration file " + this.file.getAbsolutePath() + ".");
            } catch (final IOException exception) {
                this.logger.exception(exception, "Error while trying to create configuration file " + this.file.getAbsolutePath() + ". Using default or last correctly parsed configuration values...");
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
            this.logger.exception(exception, "Error while trying to load yamlConfiguration of " + this.file.getAbsolutePath() + ". Using default or last correctly parsed configuration values...");
            return false;
        } catch (final ParserException | InvalidConfigurationException exception) {
            this.logger.error("Configuration " + this.file.getAbsolutePath() + " has syntax error(s). Trying to fix...");
            if(!this.createBroken()) {
                return false;
            }
            return this.checkFileAndLoad();
        }
        return true;
    }

}
