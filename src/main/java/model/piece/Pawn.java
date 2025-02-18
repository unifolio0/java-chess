package model.piece;

import model.game.Camp;

public abstract class Pawn extends Piece {

    protected Pawn(final Camp camp) {
        super(camp, PieceType.PAWN);
    }

    @Override
    public boolean isPawn() {
        return true;
    }
}
