package controller.menu;

import database.ChessGameService;
import model.game.ScoreCalculator;
import view.OutputView;

public class Status implements Menu {

    @Override
    public void play(ChessGameService chessGameService) {
        ScoreCalculator scoreCalculator = chessGameService.getChessGame().calculateResult();
        OutputView.printResult(scoreCalculator.getResult());
        OutputView.printWinner(scoreCalculator.getWinner());
    }
}
