package me.rarstman.rarstapi.message.impl;

import me.rarstman.rarstapi.message.Message;
import me.rarstman.rarstapi.util.StringUtil;
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
        player.sendMessage(StringUtil.replace(this.message, replaces));
    }

    @Override
    public void send(final CommandSender commandSender, final String... replaces) {
        commandSender.sendMessage(StringUtil.replace(this.message, replaces));
    }
}
