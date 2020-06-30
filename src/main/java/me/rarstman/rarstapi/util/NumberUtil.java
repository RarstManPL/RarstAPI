package me.rarstman.rarstapi.util;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumberUtil {

    public static int findFirstMissingNumber(final Set<Integer> numbers) {
        final Set<Integer> set = IntStream
                .rangeClosed(Collections.min(numbers), Collections.max(numbers))
                .boxed()
                .collect(Collectors.toSet());
        set.remove(numbers);
        return set.stream().findFirst().isPresent() ? set.stream().findFirst().get() : Collections.max(numbers) + 1;
    }

    public static boolean isNumber(final String string) {
        try {
            Integer.parseInt(string);
        } catch (final NumberFormatException exception) {
            return false;
        }
        return true;
    }

}
