package me.rarstman.rarstapi.util;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;

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

    public static boolean equalsIgnoreCase(final String string, final String... strings){
        return Arrays.stream(strings)
                .anyMatch(str -> StringUtils.equalsIgnoreCase(string, str));
    }

}
