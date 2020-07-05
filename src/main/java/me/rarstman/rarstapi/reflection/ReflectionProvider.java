package me.rarstman.rarstapi.reflection;

import me.rarstman.rarstapi.RarstAPIPlugin;
import me.rarstman.rarstapi.logger.Logger;
import me.rarstman.rarstapi.reflection.exception.ReflectionInitializeException;

public abstract class ReflectionProvider {

    protected final Logger logger;

    public ReflectionProvider() {
        this.logger = RarstAPIPlugin.getAPI().getAPILogger();
    }

    public ReflectionProvider hook() {

        try {
            this.initialize();
        } catch (final ReflectionInitializeException exception) {
            this.logger.error(exception.getMessage());
            return null;
        }
        this.logger.info("Correctly loaded '" + this.getClass().getName() + "' reflection.");
        return this;
    }

    protected abstract ReflectionProvider initialize() throws ReflectionInitializeException;

}
