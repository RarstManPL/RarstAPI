package me.rarstman.rarstapi.util;

import org.apache.commons.lang.StringUtils;

public class DateUtil {

    public static long stringToMills(final String string) {
        if (StringUtils.isNumeric(string)) {
            return Long.valueOf(string);
        }

        if (string.length() < 2 || !StringUtils.isNumeric(string.substring(0, string.length() - 1))) {
            return 0L;
        }
        long time = Long.valueOf(string.substring(0, string.length() - 1));

        switch (string.toLowerCase().charAt(string.length() - 1)) {
            case 's': {
                time *= 1000L;
                break;
            }
            case 'm': {
                time *= 60000L;
                break;
            }
            case 'h': {
                time *= 3600000L;
                break;
            }
            case 'w': {
                time *= 604800000L;
                break;
            }
            case 'o': {
                time *= 2629800000L;
                break;
            }
            case 'y': {
                time *= 31557600000L;
                break;
            }
            default: {
                time = 0L;
                break;
            }
        }
        return time;
    }

}
