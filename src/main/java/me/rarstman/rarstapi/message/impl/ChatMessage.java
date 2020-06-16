package me.rarstman.rarstapi.message.impl;

import me.rarstman.rarstapi.message.Message;
import me.rarstman.rarstapi.util.PermissionUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatMessage extends Message {

    public ChatMessage(final String message){
        super(message);
    }

    public ChatMessage(final Message message){
        this(message.getMessage());
    }

    @Override
    public void send(final CommandSender commandSender) {
        commandSender.sendMessage(this.message);
    }

    @Override
    public void send(final Player player) {
        player.sendMessage(this.message);
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
