package me.rarstman.rarstapi.util;

import java.util.regex.Pattern;

public class RegexUtil {

    private static final Pattern ipPattern = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");

    public static boolean anyMatch(final String string) {
        return ipPatternMatch(string);
    }

    public static boolean ipPatternMatch(final String string) {
        return ipPattern.matcher(string).matches();
    }

    public static Pattern getIpPattern() {
        return ipPattern;
    }

}
