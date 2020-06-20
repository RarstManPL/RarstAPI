package me.rarstman.rarstapi.inventory;

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

}
