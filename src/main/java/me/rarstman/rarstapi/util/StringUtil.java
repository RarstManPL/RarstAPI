package me.rarstman.rarstapi.util;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtil {

    public static String replace(String string, String... replaces){
        if(replaces.length < 2){
            return string;
        }

        if(replaces.length % 2 != 0){
            replaces = Arrays.copyOfRange(replaces, 0, replaces.length - 1);
        }

        for(int i = 0; i < replaces.length; i = i + 2){
            string = StringUtils.replace(string, replaces[i], replaces[i + 1]);
        }
        return string;
    }

    public static List<String> replace(final List<String> list, final String... replaces) {
        return list
                .stream()
                .map(string -> replace(string, replaces))
                .collect(Collectors.toList());
    }

    public static boolean containsIgnoreCase(final List<String> list, final String string) {
        return list
                .stream()
                .anyMatch(value -> value.equalsIgnoreCase(string));
    }

}
