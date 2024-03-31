package model.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import model.game.ChessGame;
import model.position.Moving;
import model.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessGameTest {

    @DisplayName("INIT 상태가 아닐 때 start를 입력하면 예외가 발생한다.")
    @Test
    void invalidStart() {
        final ChessGame chessGame = new ChessGame();
        chessGame.start();

        assertThatThrownBy(chessGame::start)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 게임이 진행중입니다.");
    }

    @DisplayName("RUNNING 상태가 아닐 때 move 명령어를 사용하면 예외가 발생한다.")
    @Test
    void invalidMove() {
        final ChessGame chessGame = new ChessGame();

        final Moving moving = new Moving(Position.from("a7"), Position.from("a6"));

        assertThatThrownBy(() -> chessGame.move(moving))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start를 입력해야 게임이 시작됩니다.");
    }

    @DisplayName("RUNNING 상태가 아닐 때 status 명령어를 사용하면 예외가 발생한다.")
    @Test
    void invalidStatus() {
        final ChessGame chessGame = new ChessGame();

        assertThatThrownBy(chessGame::status)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("start를 입력해야 게임이 시작됩니다.");
    }

    @DisplayName("상태가 END일 때 true값을 반환한다.")
    @Test
    void end() {
        final ChessGame chessGame = new ChessGame();
        chessGame.end();

        assertThat(chessGame.isNotEnd()).isFalse();
    }

    @DisplayName("상태가 END가 아닐 때 false값을 반환한다.")
    @Test
    void notEnd() {
        final ChessGame chessGame = new ChessGame();
        chessGame.start();

        assertThat(chessGame.isNotEnd()).isTrue();
    }
}
