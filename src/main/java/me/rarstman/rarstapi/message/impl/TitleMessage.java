package me.rarstman.rarstapi.message.impl;

import me.rarstman.rarstapi.message.Message;
import me.rarstman.rarstapi.util.PermissionUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TitleMessage extends Message {

    public TitleMessage(final String message){
        super(message);
    }

    @Override
    public void send(final CommandSender commandSender) {
        if(!(commandSender instanceof Player)){
            new ChatMessage(this).send(commandSender);
            return;
        }
        this.send((Player) commandSender);
    }

    @Override
    public void send(final Player player) {

    }

    @Override
    public void broadCast() {
        this.rarstAPIProvider.getProviderServer().getOnlinePlayers()
                .stream()
                .forEach(this::send);
    }

    @Override
    public void broadCast(final String permission) {
        this.rarstAPIProvider.getProviderServer().getOnlinePlayers()
                .stream()
                .filter(player -> PermissionUtil.hasPermission(player, permission))
                .forEach(this::send);
    }

}
