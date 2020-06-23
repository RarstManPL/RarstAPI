package me.rarstman.rarstapi.util;

public class BooleanUtil {

    public static boolean isStringStatus(final String string) {
        switch (string.toLowerCase()) {
            case "on":
            case "off": {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    public static boolean stringStatusToBoolean(final String string) {
        switch(string.toLowerCase()){
            case "on": {
                return true;
            }
            default:
            case "off": {
                return false;
            }
        }
    }

}
