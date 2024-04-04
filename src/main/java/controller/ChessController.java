package controller;

import controller.menu.Menu;
import database.ChessGameRepo;
import database.ChessGameService;
import java.util.List;
import model.game.Command;
import view.InputView;
import view.OutputView;

public class ChessController {

    // TODO : 밑에 Command 받는 부분, BoardDao를 GameDao 필드로 넣기
    private final ChessGameService chessGameService = new ChessGameService(new ChessGameRepo());

    public void run() {
        OutputView.printStartMessage();
        play();
    }

    private void play() {
        List<String> menus = InputView.readCommandList();
        if (menus.get(0).equals("start")) {
            Command.of(menus).play(chessGameService);
        } else {
            throw new IllegalArgumentException("시작안함");
        }
        while (chessGameService.isContinue()) {
            menus = InputView.readCommandList();
            if (menus.get(0).equals("end")) {
                return;
            }
            Menu menu = Command.of(menus);
            menu.play(chessGameService);
        }
    }

    /*private <T> T readWithRetry(final Consumer<ChessGame> consumer) {
        try {
            consumer.accept(chessGame);
        } catch (IllegalArgumentException e) {
            OutputView.printException(e);
            return readWithRetry(consumer, chessGame);
        }
        return null;
    }*/
}
