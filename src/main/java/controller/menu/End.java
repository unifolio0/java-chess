package controller.menu;

import model.game.ChessGame;

public class End implements Menu {

    @Override
    public void play(ChessGame chessGame) {
        chessGame.end();
    }
}
