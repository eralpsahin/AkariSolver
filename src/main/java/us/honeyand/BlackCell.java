package us.honeyand;

/**
 * Created by eralpsahin on 31.03.2017.
 */
public class BlackCell {
    int value;
    int row;
    int column;

    public BlackCell(int number, int row, int column) {
        this.value = number;
        this.row = row;
        this.column = column;
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
