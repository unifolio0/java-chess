package model.game;

import java.util.Map;
import model.piece.Piece;
import model.position.Moving;
import model.position.Position;

public class ChessGame {

    private final ChessBoard chessBoard;
    private ChessStatus chessStatus;
    private Camp camp;

    public ChessGame() {
        this.chessBoard = new ChessBoard();
        this.camp = Camp.WHITE;
        this.chessStatus = ChessStatus.INIT;
    }

    public ChessGame(ChessBoard chessBoard, Camp camp, ChessStatus chessStatus) {
        this.chessStatus = chessStatus;
        this.chessBoard = chessBoard;
        this.camp = camp;
    }

    public void start() {
        if (chessStatus == ChessStatus.INIT) {
            chessStatus = ChessStatus.RUNNING;
            return;
        }
        throw new IllegalArgumentException("이미 게임이 진행중입니다.");
    }

    public void move(Moving moving) {
        if (chessStatus == ChessStatus.RUNNING) {
            chessBoard.move(moving, camp);
            checkKing();
            camp = camp.toggle();
            return;
        }
        throw new IllegalArgumentException("start를 입력해야 게임이 시작됩니다.");
    }

    private void checkKing() {
        if (chessBoard.isKingDie()) {
            end();
        }
    }

    public ScoreCalculator status() {
        if (chessStatus == ChessStatus.RUNNING) {
            return calculateResult();
        }
        throw new IllegalArgumentException("start를 입력해야 게임이 시작됩니다.");
    }

    public ScoreCalculator calculateResult() {
        return new ScoreCalculator(chessBoard);
    }

    public void end() {
        chessStatus = ChessStatus.END;
    }

    public boolean isNotEnd() {
        return chessStatus.isNotEnd();
    }

    public Map<Position, Piece> getBoard() {
        return chessBoard.getBoard();
    }

    public ChessStatus getChessStatus() {
        return chessStatus;
    }

    public Camp getCamp() {
        return camp;
    }
}
