package controller.menu;

import database.ChessGameService;
import dto.GameBoardDto;
import model.game.ChessGame;
import model.position.Moving;
import model.position.Position;
import view.OutputView;

public class Move implements Menu {

    private final Moving moving;

    public Move(Position currentPosition, Position nextPosition) {
        this.moving = new Moving(currentPosition, nextPosition);
    }

    @Override
    public void play(ChessGameService chessGameService) {
        chessGameService.move(moving);
        ChessGame chessGame = chessGameService.getChessGame();

        if (!chessGame.isNotEnd()) {
            OutputView.printWinner(chessGame.calculateResult().getWinner());
            chessGameService.reset();
            return;
        }
        printCurrentStatus(chessGame);
    }

    private void printCurrentStatus(final ChessGame chessGame) {
        OutputView.printGameBoard(GameBoardDto.from(chessGame));
        OutputView.printCurrentCame(chessGame.getCamp());
    }
}
