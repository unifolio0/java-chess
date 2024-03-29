package model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import model.piece.Knight;
import model.piece.Piece;
import model.piece.Queen;
import model.piece.Rook;
import model.position.Moving;
import model.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessBoardTest {

    @DisplayName("기물이 없는 위치가 주어졌을 때 예외가 발생한다.")
    @Test
    void blankPosition() {
        final ChessBoard chessBoard = new ChessBoard();

        final Moving moving = new Moving(Position.from("e4"), Position.from("e5"));

        assertThatThrownBy(() -> chessBoard.move(moving, Camp.WHITE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치에 기물이 없습니다.");
    }

    @DisplayName("이동 경로에 다른 기물이 있으면 예외를 발생시킨다.")
    @Test
    void routeContainPiece() {
        final Map<Position, Piece> board = Map.of(
                Position.from("e3"), new Rook(Camp.BLACK),
                Position.from("e2"), new Queen(Camp.WHITE)
        );
        final ChessBoard chessBoard = new ChessBoard(board);

        final Moving moving = new Moving(Position.from("e2"), Position.from("e4"));

        assertThatThrownBy(() -> chessBoard.move(moving, Camp.WHITE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 다른 기물이 있습니다.");
    }

    @DisplayName("도착 지점에 같은 진영의 기물이 있으면 예외를 발생시킨다.")
    @Test
    void targetPositionIsEqualCamp() {
        final Map<Position, Piece> board = Map.of(
                Position.from("f3"), new Queen(Camp.WHITE),
                Position.from("g1"), new Knight(Camp.WHITE)
        );
        final ChessBoard chessBoard = new ChessBoard(board);

        final Moving moving = new Moving(Position.from("g1"), Position.from("f3"));

        assertThatThrownBy(() -> chessBoard.move(moving, Camp.WHITE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("도착 지점에 같은 진영의 기물이 있습니다.");
    }

    @DisplayName("상대방의 기물을 이동시키려 하면 예외가 발생한다.")
    @Test
    void invalidTurn() {
        final ChessBoard chessBoard = new ChessBoard();

        final Moving moving = new Moving(Position.from("a7"), Position.from("a6"));

        assertThatThrownBy(() -> chessBoard.move(moving, Camp.WHITE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("자신의 기물만 이동 가능합니다.");
    }
}
