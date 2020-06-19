package me.rarstman.rarstapi.listener;

import me.rarstman.rarstapi.util.ListenerUtil;
import org.bukkit.event.Listener;

public abstract class ListenerProvider implements Listener {

    public ListenerProvider register() {
        ListenerUtil.register(this);
        return this;
    }

}
