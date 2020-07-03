package me.rarstman.rarstapi.command.impl;

import me.rarstman.rarstapi.command.CommandProvider;
import me.rarstman.rarstapi.configuration.ConfigManager;
import me.rarstman.rarstapi.configuration.impl.RarstAPIConfig;
import org.bukkit.command.CommandSender;

public class RarstAPICommand extends CommandProvider {

    private final RarstAPIConfig config = ConfigManager.getConfig(RarstAPIConfig.class);

    public RarstAPICommand() {
        super(ConfigManager.getConfig(RarstAPIConfig.class).rarstAPICommandData, "rarstapi.command.rarstapi", false);
    }

    @Override
    public void onExecute(final CommandSender commandSender, final String[] args) {
        if(!this.config.reload() || !this.rarstAPIMessages.reload()){
            this.rarstAPIMessages.configurationNotCorrectlyReloaded.send(commandSender);
            return;
        }
        this.rarstAPIMessages.configurationReloaded.send(commandSender);
    }

}
