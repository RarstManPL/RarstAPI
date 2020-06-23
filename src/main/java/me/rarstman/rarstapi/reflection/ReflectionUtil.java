package me.rarstman.rarstapi.reflection;

import java.util.List;
import java.util.stream.Collectors;

public class ReflectionUtil {

    public static Enum parseEnum(final Class<? extends Enum> enumClazz, final String enumName) {
        return Enum.valueOf(enumClazz, enumName);
    }

    public static List<Enum> parseEnums(final Class<? extends Enum> enumClazz, final List<String> enumsList) {
        return enumsList
                .stream()
                .filter(enumName -> parseEnum(enumClazz, enumName) != null)
                .map(enumName -> parseEnum(enumClazz, enumName))
                .collect(Collectors.toList());
    }
}
