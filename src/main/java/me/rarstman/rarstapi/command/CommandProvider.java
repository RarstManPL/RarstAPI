package me.rarstman.rarstapi.command;

import me.rarstman.rarstapi.configuration.ConfigManager;
import me.rarstman.rarstapi.configuration.impl.RarstAPIMessages;
import me.rarstman.rarstapi.util.CommandUtil;
import me.rarstman.rarstapi.util.PermissionUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CommandProvider extends Command {

    public final String permission;

    private final boolean onlyPlayer;
    private final boolean enabled;

    public final RarstAPIMessages rarstAPIMessages;

    public CommandProvider(final String name, final List<String> aliases, final String description, final String usage, final String permission, final boolean onlyPlayer, final boolean enabled) {
        super(name, description, usage, aliases);

        this.permission = permission;
        this.onlyPlayer = onlyPlayer;
        this.enabled = enabled;

        this.rarstAPIMessages = ConfigManager.getConfig(RarstAPIMessages.class);
    }

    public CommandProvider(final CommandData commandData, final String permission, final boolean onlyPlayer) {
        this(commandData.getName(), commandData.getAliases(), commandData.getDescription(), commandData.getUsage(), permission, onlyPlayer, commandData.isEnabled());
    }

    public CommandProvider register() {
        if(!this.enabled) {
            return null;
        }

        if(!CommandUtil.register(this)) {
            return null;
        }
        return this;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public String getPermission() {
        return this.permission;
    }

    @Override
    public boolean execute(final CommandSender commandSender, final String label, final String[] args) {
        if(this.onlyPlayer && !(commandSender instanceof Player)) {
            this.rarstAPIMessages.onlyPlayer.send(commandSender);
            return true;
        }

        if(!PermissionUtil.hasPermission(commandSender, this.permission)) {
            this.rarstAPIMessages.noPermission.send(commandSender, "{PERMISSION}", this.permission);
            return true;
        }
        this.onExecute(commandSender, args);
        return true;
    }

    @Override
    public List<String> tabComplete(final CommandSender commandSender, final String alias, final String[] args) throws IllegalArgumentException {
        if(!PermissionUtil.hasPermission(commandSender, this.permission)) {
            return new ArrayList<>();
        }
        final List<String> completions = this.onTabComplete(commandSender, alias, args)
                .stream()
                .filter(string -> string.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                .collect(Collectors.toList());
        return completions == null || completions.isEmpty() ? new ArrayList<>() : completions;
    }

    public abstract void onExecute(final CommandSender commandSender, final String[] args);

    public abstract List<String> onTabComplete(final CommandSender commandSender, final String alias, final String[] args) throws IllegalArgumentException;

}
