package model;

import database.DBConnection;
import database.DBService;
import dto.ChessGameDto;
import java.util.Map;
import model.piece.Piece;
import model.position.Moving;
import model.position.Position;

public class ChessGame {

    private final DBService dbService;
    private ChessStatus chessStatus;
    private ChessBoard chessBoard;
    private Camp camp;

    public ChessGame() {
        this.dbService = new DBService();
        this.chessStatus = ChessStatus.INIT;
        if (dbService.isContinue()) {
            ChessGameDto chessGameDto = dbService.reload();
            this.chessBoard = new ChessBoard(chessGameDto.board());
            this.camp = chessGameDto.camp();
            return;
        }
        this.chessBoard = new ChessBoard();
    }

    public void start() {
        if (chessStatus == ChessStatus.INIT) {
            chessStatus = ChessStatus.RUNNING;
            if (dbService.isContinue()) {
                return;
            }
            this.camp = Camp.WHITE;
            dbService.saveAll(new ChessGameDto(chessBoard.getBoard(), camp));
            return;
        }
        throw new IllegalArgumentException("이미 게임이 진행중입니다.");
    }

    public void move(Moving moving) {
        if (chessStatus == ChessStatus.RUNNING) {
            Piece source = chessBoard.findPiece(moving.getCurrentPosition());
            if (chessBoard.checkPosition(moving.getNextPosition())) {
                dbService.updatePiece(moving.getNextPosition(), source);
            } else {
                dbService.savePiece(moving.getNextPosition(), source);
            }
            chessBoard.move(moving, camp);
            dbService.deletePiece(moving.getCurrentPosition());
            checkKing();
            camp = camp.toggle();
            dbService.updateCamp(camp);
            return;
        }
        throw new IllegalArgumentException("start를 입력해야 게임이 시작됩니다.");
    }

    private void checkKing() {
        if (chessBoard.isKingDie()) {
            dbService.reset();
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

    public Camp getCamp() {
        return camp;
    }
}
