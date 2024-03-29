package model.piece;

import java.util.HashMap;
import java.util.Map;
import model.game.Camp;

public class PieceGenerator {

    private static final Map<String, Piece> CASH;

    static {
        CASH = new HashMap<>();
        CASH.put("BISHOP_BLACK", new Bishop(Camp.BLACK));
        CASH.put("PAWN_BLACK", new BlackPawn());
        CASH.put("KING_BLACK", new King(Camp.BLACK));
        CASH.put("QUEEN_BLACK", new Queen(Camp.BLACK));
        CASH.put("ROOK_BLACK", new Rook(Camp.BLACK));
        CASH.put("KNIGHT_BLACK", new Knight(Camp.BLACK));
        CASH.put("BISHOP_WHITE", new Bishop(Camp.WHITE));
        CASH.put("PAWN_WHITE", new WhitePawn());
        CASH.put("KING_WHITE", new King(Camp.WHITE));
        CASH.put("QUEEN_WHITE", new Queen(Camp.WHITE));
        CASH.put("ROOK_WHITE", new Rook(Camp.WHITE));
        CASH.put("KNIGHT_WHITE", new Knight(Camp.WHITE));
    }

    private PieceGenerator() {

    }

    public static Piece getPiece(String input) {
        if (CASH.containsKey(input)) {
            return CASH.get(input);
        }
        throw new IllegalArgumentException("해당 기물은 존재하지 않습니다.");
    }
}
