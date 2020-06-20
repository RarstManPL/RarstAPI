package me.rarstman.rarstapi.util;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class ColorUtil {

    public static String color(final String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> color(final List<String> list){
        return list
                .stream()
                .map(ColorUtil::color)
                .collect(Collectors.toList());
    }

}
