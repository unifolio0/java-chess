package dto;

import java.util.Map;
import model.game.Camp;
import model.piece.Piece;
import model.position.Position;

public record ChessGameDto(Map<Position, Piece> board, Camp camp) {
}
