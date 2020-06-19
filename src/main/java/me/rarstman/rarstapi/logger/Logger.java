package me.rarstman.rarstapi.logger;

import org.bukkit.Bukkit;

import java.util.Arrays;

public class Logger {

    private final java.util.logging.Logger logger;

    public Logger(final java.util.logging.Logger logger){
        this.logger = logger;
    }

    public void info(final String message){
        this.logger.info("[" + this.logger.getName() + "] " + message);
    }

    public void warning(final String message){
        this.logger.warning("[" + this.logger.getName() + "] " + message);
    }

    public void error(final String message){
        this.logger.severe("[" + this.logger.getName() + "] " + message);
    }

    public void exception(final Exception exception, final String message){
        this.error(" ");
        this.error("An exception occured");
        this.error(" ");
        this.error("Informations:");
        this.error(" > Java version: " + System.getProperty("java.version"));
        this.error(" > Server version: " + Bukkit.getVersion());
        this.error(" > Author message: " + message);
        this.error(" > Exception message: " + exception.getMessage());
        this.error(" ");
        this.error("StackTrace:");
        Arrays.stream(exception.getStackTrace())
                .map(stackTraceElement -> " " + stackTraceElement.toString())
                .forEach(this::error);
        this.error(" ");
    }
}
