package me.rarstman.rarstapi.util;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class DateUtil {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

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
            case 'd': {
                time *= 86400000L;
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

    public static String stringFromMills(long time) {
        if (time == 0) {
            return "0";
        }
        long days = TimeUnit.MILLISECONDS.toDays(time);
        time -= TimeUnit.DAYS.toMillis(days);

        long hours = TimeUnit.MILLISECONDS.toHours(time);
        time -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        time -= TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.MILLISECONDS.toSeconds(time);
        time -= TimeUnit.SECONDS.toMillis(seconds);

        StringBuilder stringBuilder = new StringBuilder();

        if (days > 0) {
            stringBuilder.append(days);

            switch ((int) days) {
                case 1: {
                    stringBuilder.append(" dzień ");
                    break;
                }
                default: {
                    stringBuilder.append(" dni ");
                    break;
                }
            }
        }

        if (hours > 0) {
            stringBuilder.append(hours);

            switch ((int) hours) {
                case 1: {
                    stringBuilder.append(" godzine ");
                    break;
                }
                case 2:
                case 3:
                case 4:
                case 22:
                case 23:
                case 24: {
                    stringBuilder.append(" godziny ");
                    break;
                }
                default: {
                    stringBuilder.append(" godzin ");
                    break;
                }
            }
        }

        if (minutes > 0) {
            stringBuilder.append(minutes);

            switch ((int) minutes) {
                case 1: {
                    stringBuilder.append(" minute ");
                    break;
                }
                case 2:
                case 3:
                case 4:
                case 22:
                case 23:
                case 24:
                case 32:
                case 33:
                case 34:
                case 42:
                case 43:
                case 44:
                case 52:
                case 53:
                case 54: {
                    stringBuilder.append(" minuty ");
                    break;
                }
                default: {
                    stringBuilder.append(" minut ");
                    break;
                }
            }
        }

        if (seconds > 0) {
            stringBuilder.append(seconds);

            switch ((int) seconds) {
                case 1: {
                    stringBuilder.append(" sekunde ");
                    break;
                }
                case 2:
                case 3:
                case 4:
                case 22:
                case 23:
                case 24:
                case 32:
                case 33:
                case 34:
                case 42:
                case 43:
                case 44:
                case 52:
                case 53:
                case 54: {
                    stringBuilder.append(" sekundy ");
                    break;
                }
                default: {
                    stringBuilder.append(" sekund ");
                    break;
                }
            }
        }
        return (stringBuilder.toString()).substring(0, stringBuilder.toString().length() - 1);
    }

    public static String formatDateFromMills(final long date) {
        final GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(date);
        return simpleDateFormat.format(gregorianCalendar.getTime());
    }

}