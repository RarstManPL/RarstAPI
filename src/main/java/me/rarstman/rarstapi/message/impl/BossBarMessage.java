package me.rarstman.rarstapi.message.impl;

import me.rarstman.rarstapi.message.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BossBarMessage extends Message {

    public BossBarMessage(final String message){
        super(message);
    }

    @Override
    public void send(final Player player, final String... replaces) {

    }

    @Override
    public void send(final CommandSender commandSender, final String... replaces) {
        if(!(commandSender instanceof Player)){
            new ChatMessage(this).send(commandSender, replaces);
            return;
        }
        this.send((Player) commandSender, replaces);
    }

}
