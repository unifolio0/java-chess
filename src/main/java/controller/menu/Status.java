package controller.menu;

import model.game.ChessGame;
import model.game.ScoreCalculator;
import view.OutputView;

public class Status implements Menu {

    @Override
    public void play(ChessGame chessGame) {
        ScoreCalculator scoreCalculator = chessGame.status();
        OutputView.printResult(scoreCalculator.getResult());
        OutputView.printWinner(scoreCalculator.getWinner());
    }
}
