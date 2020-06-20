package me.rarstman.rarstapi.util;

public class BooleanUtil {

    public static Boolean stringStatusToBoolean(final String string){
        Boolean status = null;

        switch(string.toLowerCase()){
            case "on": {
                status = true;
                break;
            }
            case "off": {
                status = false;
                break;
            }
        }
        return status;
    }

}
