package me.rarstman.rarstapi.command;

import me.rarstman.rarstapi.configuration.ConfigManager;
import me.rarstman.rarstapi.configuration.impl.RarstAPIConfig;
import me.rarstman.rarstapi.message.Message;
import me.rarstman.rarstapi.util.CommandUtil;
import me.rarstman.rarstapi.util.PermissionUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class CommandProvider extends Command {

    private final String permission;
    private final boolean onlyPlayer;

    private final Message noPermissionMessage;
    private final Message onlyPlayerMessage;

    public CommandProvider(final String name, final List<String> aliases, final String description, final String usage, final String permission, final boolean onlyPlayer) {
        super(name, description, usage, aliases);

        this.permission = permission;
        this.onlyPlayer = onlyPlayer;

        final RarstAPIConfig rarstAPIConfig = ConfigManager.getConfig(RarstAPIConfig.class);
        this.noPermissionMessage = rarstAPIConfig.noPermission;
        this.onlyPlayerMessage = rarstAPIConfig.onlyPlayer;
    }

    public CommandProvider(final CommandData commandData, final String permission, final boolean onlyPlayer) {
        this(commandData.getName(), commandData.getAliases(), commandData.getDescription(), commandData.getUsage(), permission, onlyPlayer);
    }

    public CommandProvider register() {
        CommandUtil.register(this);
        return this;
    }

    @Override
    public boolean execute(final CommandSender commandSender, final String label, final String[] args) {
        if(this.onlyPlayer && !(commandSender instanceof Player)) {
            this.onlyPlayerMessage.send(commandSender);
            return true;
        }

        if(!PermissionUtil.hasPermission(commandSender, this.permission)) {
            this.noPermissionMessage.send(commandSender, "{permission}", this.permission);
            return true;
        }
        this.onExecute(commandSender, args);
        return true;
    }

    public abstract void onExecute(final CommandSender commandSender, final String[] args);
}
