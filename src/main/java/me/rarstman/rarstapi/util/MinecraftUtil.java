package me.rarstman.rarstapi.util;

import org.apache.commons.lang.StringUtils;

public class MinecraftUtil {

    public static long getMinecraftTime(final String string){
        long time = -1L;

        switch (string.toLowerCase()) {
            case "daystart":
            case "day": {
                time = 0L;
                break;
            }
            case "noon":
            case "midday": {
                time = 6000L;
                break;
            }
            case "dawn":
            case "sunrise": {
                time = 23000L;
                break;
            }
            case "morning": {
                time = 1000L;
                break;
            }
            case "afternoon": {
                time = 9000L;
                break;
            }
            case "sundown":
            case "nightfall":
            case "dusk":
            case "sunset": {
                time = 12000L;
                break;
            }
            case "nightstart":
            case "night": {
                time = 14000L;
                break;
            }
            case "midnight": {
                time = 18000L;
                break;
            }
            default: {
                if (!StringUtils.isNumeric(string)) {
                    break;
                }
                time = Long.valueOf(string);
                break;
            }
        }
        return time > 240000L ? -1L :  time;
    }

    public static Weather parseWeather(final String string){
        Boolean stormy = null;
        Boolean thundering = null;

        switch (string.toLowerCase()) {
            case "thunder": {
                stormy = true;
                thundering = true;
                break;
            }
            case "rain":
            case "storm": {
                stormy = true;
                thundering = false;
                break;
            }
            case "clear":
            case "sun": {
                stormy = false;
                thundering = false;
                break;
            }
        }
        return new Weather(thundering, stormy);
    }

    public static String minecraftHourFromMills(long time){
        time = time - 18000L + 24000L;
        final int hours = Math.round(time/1000L);
        final int minutes = Math.round((time-hours*1000L)/60L);
        return (hours < 10 ? "0" + String.valueOf(hours) : String.valueOf(hours)) + ":" + (minutes < 10 ? "0" + String.valueOf(minutes) : String.valueOf(minutes));
    }

    public static class Weather {

        private final Boolean thundering;
        private final Boolean stormy;

        public Weather(final Boolean thundering, final Boolean stormy) {
            this.thundering = thundering;
            this.stormy = stormy;
        }

        public Boolean isStormy() {
            return this.stormy;
        }

        public Boolean isThundering() {
            return this.thundering;
        }

    }

}
