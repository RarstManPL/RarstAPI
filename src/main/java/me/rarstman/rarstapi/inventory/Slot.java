package me.rarstman.rarstapi.inventory;

public class Slot {

    private final int column;
    private final int row;
    private final int slot;

    public Slot(final int column, final int row) {
        this.column = column;
        this.row = row;
        this.slot = (this.row + (this.column - 1) * 9) - 1;
    }

    public int getColumn() {
        return this.column;
    }

    public int getRow() {
        return this.row;
    }

    public int getSlot() {
        return this.slot;
    }

}
