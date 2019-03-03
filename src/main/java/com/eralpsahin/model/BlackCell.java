package com.eralpsahin.model;

/**
 * Modified by eralpsahin on 03.03.2019.
 */
public class BlackCell {
    private int value;
    int row;
    int column;
    int neighbor;

    BlackCell(int value, int row, int column) {
        this.value = value;
        this.row = row;
        this.column = column;
        neighbor = 0;
    }

    int getNeighbor() {
        return neighbor;
    }

    int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "BlackCell[" + row + "][" + column + "]: " + value;
    }
}