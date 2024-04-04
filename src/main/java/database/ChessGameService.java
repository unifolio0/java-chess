package database;

import model.game.ChessGame;
import model.game.ChessStatus;
import model.position.Moving;

public class ChessGameService {

    private final ChessGameRepo chessGameRepo;

    public ChessGameService(ChessGameRepo chessGameRepo) {
        this.chessGameRepo = chessGameRepo;
    }

    public boolean isContinue() {
        return chessGameRepo.isContinue();
    }

    public void move(Moving moving) {
        ChessGame chessGame = getChessGame();
        chessGame.move(moving);
        chessGameRepo.updateGame(chessGame);
    }

    public ChessGame getChessGame() {
        return chessGameRepo.reload();
    }

    public void start() {
        if (isContinue()) {
            return;
        }
        ChessGame chessGame = new ChessGame();
        chessGame.start();
        chessGameRepo.saveAll(chessGame);
    }

    public ChessStatus getStatus() {
        return chessGameRepo.reload().getChessStatus();
    }

    public void reset() {
        chessGameRepo.reset();
    }
}
