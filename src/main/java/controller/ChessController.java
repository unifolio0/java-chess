package controller;

import controller.menu.Menu;
import java.util.function.Consumer;
import model.game.ChessGame;
import model.game.Command;
import view.InputView;
import view.OutputView;

public class ChessController {

    public void run() {
        ChessGame chessGame = new ChessGame();
        OutputView.printStartMessage();
        readWithRetry(this::play, chessGame);
    }

    private void play(final ChessGame chessGame) {
        while (chessGame.isNotEnd()) {
            Menu menu = Command.of(InputView.readCommandList());
            menu.play(chessGame);
        }
        OutputView.printWinner(chessGame.calculateResult().getWinner());
    }

    private <T> T readWithRetry(final Consumer<ChessGame> consumer, final ChessGame chessGame) {
        try {
            consumer.accept(chessGame);
        } catch (IllegalArgumentException e) {
            OutputView.printException(e);
            return readWithRetry(consumer, chessGame);
        }
        return null;
    }
}
