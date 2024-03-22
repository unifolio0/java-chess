package model.piece;

import java.util.Set;
import model.Camp;
import model.position.Moving;
import model.position.Position;

public final class King extends Piece {

    public King(final Camp camp) {
        super(camp, new PieceName("k"));
    }

    @Override
    public Set<Position> getMoveRoute(final Moving moving) {
        if (!canMovable(moving)) {
            throw new IllegalArgumentException("해당 기물이 이동할 수 없는 위치입니다.");
        }
        return Set.of();
    }

    @Override
    protected boolean canMovable(final Moving moving) {
        final Position currentPosition = moving.getCurrentPosition();
        final Position nextPosition = moving.getNextPosition();

        if (moving.isNotMoved()) {
            return false;
        }
        final int currentRow = currentPosition.getRowIndex();
        final int currentColumn = currentPosition.getColumnIndex();

        final int nextRow = nextPosition.getRowIndex();
        final int nextColumn = nextPosition.getColumnIndex();

        return Math.abs(nextRow - currentRow) <= 1 && Math.abs(nextColumn - currentColumn) <= 1;
    }

    @Override
    public String toString() {
        return getName();
    }
}
