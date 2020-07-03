package me.rarstman.rarstapi.inventory;

import java.util.Arrays;
import java.util.Optional;

public enum Rows {

    ONE(1, 9),
    TWO(2, 18),
    THREE(3, 27),
    FOUR(4, 36),
    FIVE(5, 45),
    SIX(6, 54);

    public int columns;
    public int slots;

    Rows(final int columns, final int slots) {
        this.columns = columns;
        this.slots = slots;
    }

    public static Optional<Rows> getBySlots(final int slots) {
        return Arrays.stream(Rows.values())
                .filter(rows -> rows.slots == slots)
                .findAny();
    }

    public static Optional<Rows> getByColumns(final int columns) {
        return Arrays.stream(Rows.values())
                .filter(rows -> rows.columns == columns)
                .findAny();
    }

}
