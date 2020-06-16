package me.rarstman.rarstapi.logger;

import me.rarstman.rarstapi.RarstAPI;
import me.rarstman.rarstapi.RarstAPIProvider;

import java.util.Arrays;

public class Logger {

    private final java.util.logging.Logger logger;

    public Logger(final java.util.logging.Logger logger){
        this.logger = logger;
    }

    public void info(final String message){
        this.logger.info(message);
    }

    public void warning(final String message){
        this.logger.warning(message);
    }

    public void error(final String message){
        this.logger.severe(message);
    }

    public void exception(final String authorMessage, final String exceptionMessage, final StackTraceElement[] stackTraceElements){
        final RarstAPIProvider rarstAPIProvider = RarstAPI.getAPI().getRarstAPIProvider();

        this.error(" ");
        this.error(rarstAPIProvider.getProviderName() + "'s exception:");
        this.error(" ");
        this.error("Informations:");
        this.error(" > Java version: " + System.getProperty("java.version"));
        this.error(" > Server version: " + rarstAPIProvider.getProviderServer().getVersion());
        this.error(" > Plugin version: " + rarstAPIProvider.getProviderVersion());
        this.error(" > Author message: " + authorMessage);
        this.error(" > Exception message: " + exceptionMessage);
        this.error(" ");
        this.error("StackTrace:");
        Arrays.stream(stackTraceElements)
                .map(stackTraceElement -> " " + stackTraceElement.toString())
                .forEach(this::error);
        this.error(" ");
    }
}
