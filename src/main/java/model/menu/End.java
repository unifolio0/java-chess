package model.menu;

import java.util.List;
import model.ChessGame;

public class End implements Menu {

    @Override
    public Menu play(final List<String> input, final ChessGame chessGame) {
        throw new IllegalArgumentException("게임이 종료되었습니다");
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void play2(ChessGame chessGame) {
        chessGame.end();
    }
}
