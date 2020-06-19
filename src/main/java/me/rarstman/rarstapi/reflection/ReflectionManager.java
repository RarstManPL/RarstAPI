package me.rarstman.rarstapi.reflection;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ReflectionManager {

    private static final Map<Class<? extends ReflectionProvider>, ReflectionProvider> reflections = new HashMap<>();

    public static void registerReflection(final ReflectionProvider reflection){
        if(reflection.hook() == null) {
            return;
        }
        reflections.put(reflection.getClass(), reflection);
    }

    public static void registerReflections(final ReflectionProvider... reflections) {
        Arrays.stream(reflections)
                .forEach(ReflectionManager::registerReflection);
    }

    public static <A extends ReflectionProvider> A getReflection(final Class<A> reflectionClass){
        return (A) reflections.get(reflectionClass);
    }
}
