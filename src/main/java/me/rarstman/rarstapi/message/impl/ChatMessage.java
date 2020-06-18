package me.rarstman.rarstapi.message.impl;

import me.rarstman.rarstapi.message.Message;
import me.rarstman.rarstapi.util.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatMessage extends Message {

    public ChatMessage(final String message){
        super(message);
    }

    public ChatMessage(final Message message){
        super(message);
    }

    @Override
    public void send(final Player player, final String... replaces) {
        ChatUtil.sendMessage(player, this.message, replaces);
    }

    @Override
    public void send(final CommandSender commandSender, final String... replaces) {
        ChatUtil.sendMessage(commandSender, this.message, replaces);
    }

}
