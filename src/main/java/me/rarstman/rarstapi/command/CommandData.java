package me.rarstman.rarstapi.command;

import java.util.List;

public class CommandData {

    private final String name;
    private final List<String> aliases;
    private final String description;
    private final String usage;
    private final boolean enabled;

    public CommandData(final String name, final List<String> aliases, final String description, final String usage, final boolean enabled) {
        this.name = name;
        this.aliases = aliases;
        this.description = description;
        this.usage = usage;
        this.enabled = enabled;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getAliases() {
        return this.aliases;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUsage() {
        return this.usage;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

}
