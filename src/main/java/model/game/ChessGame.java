package model.game;

import database.ChessGameDBService;
import dto.ChessGameDto;
import java.util.Map;
import model.piece.Piece;
import model.position.Moving;
import model.position.Position;

public class ChessGame {

    private final ChessGameDBService chessGameDbService;
    private final ChessBoard chessBoard;
    private ChessStatus chessStatus;
    private Camp camp;

    public ChessGame() {
        this.chessGameDbService = new ChessGameDBService();
        this.chessStatus = ChessStatus.INIT;
        if (chessGameDbService.isContinue()) {
            ChessGameDto chessGameDto = chessGameDbService.reload();
            this.chessBoard = new ChessBoard(chessGameDto.board());
            this.camp = chessGameDto.camp();
            return;
        }
        this.chessBoard = new ChessBoard();
    }

    public void start() {
        if (chessStatus == ChessStatus.INIT) {
            chessStatus = ChessStatus.RUNNING;
            if (chessGameDbService.isContinue()) {
                return;
            }
            this.camp = Camp.WHITE;
            chessGameDbService.saveAll(new ChessGameDto(chessBoard.getBoard(), camp));
            return;
        }
        throw new IllegalArgumentException("이미 게임이 진행중입니다.");
    }

    public void move(Moving moving) {
        if (chessStatus == ChessStatus.RUNNING) {
            chessGameDbService.moveUpdate(chessBoard, moving);
            chessBoard.move(moving, camp);
            checkKing();
            camp = camp.toggle();
            chessGameDbService.updateCamp(camp);
            return;
        }
        throw new IllegalArgumentException("start를 입력해야 게임이 시작됩니다.");
    }

    private void checkKing() {
        if (chessBoard.isKingDie()) {
            chessGameDbService.reset();
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
