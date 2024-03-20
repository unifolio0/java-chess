package model.position;

import java.util.Objects;

public class Position {

    //TODO : 64개 캐싱

    private final Column column;
    private final Row row;

    public Position(Column column, Row row) {
        this.column = column;
        this.row = row;
    }

    public static Position from(String command) {
        return new Position(Column.from(command.charAt(0)), Row.from(command.charAt(1)));
    }

    public int getRowIndex() {
        return row.getIndex();
    }

    public int getColumnIndex() {
        return column.getIndex();
    }

    public Row getRow() {
        return row;
    }

    public Column getColumn() {
        return column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public boolean equals(final Object target) {
        if (this == target) {
            return true;
        }
        if (!(target instanceof Position position)) {
            return false;
        }
        return Objects.equals(row, position.row) && Objects.equals(column, position.column);
    }

    @Override
    public String toString() {
        return column.getValue() + row.getValue() + "  (" + row.getIndex() + ", " + column.getIndex() + ")";
    }
}
