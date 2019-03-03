package com.eralpsahin.model;

/**
 * Created by eralpsahin on 31.03.2017.
 */
public class BlackCell {
    int value;
    int row;
    int column;
    int neighbor;
    public BlackCell(int value, int row, int column) {
        this.value = value;
        this.row = row;
        this.column = column;
        neighbor = 0;
    }

    public int getNeighbor() {
        return neighbor;
    }

    public int getValue() {
        return value;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "BlackCell["+row+"]["+column+"]: " + value;
    }
}