package controller.menu;

import dto.GameBoardDto;
import model.game.ChessGame;
import view.OutputView;

public class Start implements Menu {

    @Override
    public void play(ChessGame chessGame) {
        chessGame.start();
        printCurrentStatus(chessGame);
    }

    private void printCurrentStatus(final ChessGame chessGame) {
        OutputView.printGameBoard(GameBoardDto.from(chessGame));
        OutputView.printCurrentCame(chessGame.getCamp());
    }
}
