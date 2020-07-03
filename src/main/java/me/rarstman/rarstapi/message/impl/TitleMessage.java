package me.rarstman.rarstapi.message.impl;

import me.rarstman.rarstapi.message.Message;
import me.rarstman.rarstapi.util.ChatUtil;
import me.rarstman.rarstapi.util.ColorUtil;
import me.rarstman.rarstapi.util.StringUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TitleMessage extends Message {

    private String title;
    private int fadein;
    private int stay;
    private int fadeout;

    public TitleMessage(final String title, final String subtitle, final int fadein, final int stay, final int fadeout) {
        super(subtitle);

        this.title = title;
        this.fadein = fadein;
        this.stay = stay;
        this.fadeout = fadeout;
    }

    public TitleMessage(final String title, final String subtitle, final int stay) {
        this(title, subtitle, stay/6, stay, stay/6);
    }

    public TitleMessage(final String title, final String subtitle) {
        this(title, subtitle, subtitle.length()/6);
    }

    public TitleMessage(final String message) {
        this(null, message);
    }

    public TitleMessage(final Message message) {
        this(message.getMessage());
    }

    @Override
    public void send(final Player player, final String... replaces) {
        if(this.message.length() < 50) {
            player.sendTitle(ColorUtil.color(StringUtil.replace(this.title == null ? "" : this.title, replaces)), StringUtil.replace(this.message, replaces), this.fadein, this.stay, this.fadeout);
            return;
        }
        new ChatMessage(this).send(player, replaces);
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
