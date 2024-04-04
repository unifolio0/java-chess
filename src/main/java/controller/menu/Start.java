package controller.menu;

import database.ChessGameService;
import dto.GameBoardDto;
import model.game.ChessGame;
import view.OutputView;

public class Start implements Menu {

    @Override
    public void play(ChessGameService chessGameService) {
        chessGameService.start();
        printCurrentStatus(chessGameService.getChessGame());
    }

    private void printCurrentStatus(final ChessGame chessGame) {
        OutputView.printGameBoard(GameBoardDto.from(chessGame));
        OutputView.printCurrentCame(chessGame.getCamp());
    }
}
