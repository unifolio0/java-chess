package database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import dto.ChessGameDto;
import java.util.HashMap;
import java.util.Map;
import model.game.Camp;
import model.game.ChessBoard;
import model.piece.King;
import model.piece.Piece;
import model.piece.WhitePawn;
import model.position.Moving;
import model.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChessGameDBServiceTest {

    @DisplayName("이전 게임의 진행 상태를 불러온다.")
    @Test
    void reload() {
        Map<Position, Piece> board = Map.of(
                Position.from("a3"), new King(Camp.BLACK),
                Position.from("c4"), new WhitePawn()
        );
        Map<Integer, Camp> current_turn = Map.of(0, Camp.BLACK);
        FakeJdbcChessBoardDao fakeJdbcChessBoardDao = new FakeJdbcChessBoardDao(board);
        FakeJdbcChessGameDao fakeJdbcChessGameDao = new FakeJdbcChessGameDao(current_turn);
        ChessGameDBService chessGameDBService = new ChessGameDBService(fakeJdbcChessBoardDao, fakeJdbcChessGameDao);

        ChessGameDto chessGameDto = new ChessGameDto(board, current_turn.get(0));

        assertThat(chessGameDBService.reload()).isEqualTo(chessGameDto);
    }

    @DisplayName("이전 게임의 저장기록이 없으면 false를 반환한다..")
    @Test
    void isNotContinue() {
        FakeJdbcChessBoardDao fakeJdbcChessBoardDao = new FakeJdbcChessBoardDao();
        FakeJdbcChessGameDao fakeJdbcChessGameDao = new FakeJdbcChessGameDao();
        ChessGameDBService chessGameDBService = new ChessGameDBService(fakeJdbcChessBoardDao, fakeJdbcChessGameDao);

        assertThat(chessGameDBService.isContinue()).isFalse();
    }

    @DisplayName("이전 게임의 저장기록이 있으면 true를 반환한다.")
    @Test
    void isContinue() {
        Map<Integer, Camp> current_turn = Map.of(0, Camp.BLACK);
        FakeJdbcChessBoardDao fakeJdbcChessBoardDao = new FakeJdbcChessBoardDao();
        FakeJdbcChessGameDao fakeJdbcChessGameDao = new FakeJdbcChessGameDao(current_turn);
        ChessGameDBService chessGameDBService = new ChessGameDBService(fakeJdbcChessBoardDao, fakeJdbcChessGameDao);

        assertThat(chessGameDBService.isContinue()).isTrue();
    }

    @DisplayName("현재 보드의 상태를 저장한다.")
    @Test
    void saveAll() {
        FakeJdbcChessBoardDao fakeJdbcChessBoardDao = new FakeJdbcChessBoardDao();
        FakeJdbcChessGameDao fakeJdbcChessGameDao = new FakeJdbcChessGameDao();
        ChessGameDBService chessGameDBService = new ChessGameDBService(fakeJdbcChessBoardDao, fakeJdbcChessGameDao);

        Map<Position, Piece> board = Map.of(
                Position.from("a3"), new King(Camp.BLACK),
                Position.from("c4"), new WhitePawn()
        );
        Map<Integer, Camp> current_turn = Map.of(0, Camp.BLACK);
        ChessGameDto chessGameDto = new ChessGameDto(board, current_turn.get(0));

        chessGameDBService.saveAll(chessGameDto);

        assertAll(
                () -> assertThat(fakeJdbcChessBoardDao.getFakeBoard()).isEqualTo(board),
                () -> assertThat(fakeJdbcChessGameDao.getCurrent_turn()).isEqualTo(current_turn)
        );
    }

    @DisplayName("기물의 움직인 뒤의 보드 상태를 DB에 업데이트 한다.")
    @Test
    void moveUpdate() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(Position.from("a3"), new King(Camp.BLACK));
        board.put(Position.from("c4"), new WhitePawn());
        ChessBoard chessBoard = new ChessBoard(board);
        Moving moving = new Moving(Position.from("a3"), Position.from("a4"));

        FakeJdbcChessBoardDao fakeJdbcChessBoardDao = new FakeJdbcChessBoardDao(board);
        FakeJdbcChessGameDao fakeJdbcChessGameDao = new FakeJdbcChessGameDao();
        ChessGameDBService chessGameDBService = new ChessGameDBService(fakeJdbcChessBoardDao, fakeJdbcChessGameDao);

        Map<Position, Piece> excepted = new HashMap<>();
        excepted.put(Position.from("a4"), new King(Camp.BLACK));
        excepted.put(Position.from("c4"), new WhitePawn());

        chessGameDBService.moveUpdate(chessBoard, moving);

        assertThat(fakeJdbcChessBoardDao.getFakeBoard()).isEqualTo(excepted);
    }

    @DisplayName("저장되어 있는 턴을 현재 턴으로 업데이트한다.")
    @Test
    void updateCamp() {
        Map<Integer, Camp> current_turn = new HashMap<>();
        current_turn.put(0, Camp.BLACK);
        FakeJdbcChessBoardDao fakeJdbcChessBoardDao = new FakeJdbcChessBoardDao();
        FakeJdbcChessGameDao fakeJdbcChessGameDao = new FakeJdbcChessGameDao(current_turn);
        ChessGameDBService chessGameDBService = new ChessGameDBService(fakeJdbcChessBoardDao, fakeJdbcChessGameDao);

        chessGameDBService.updateCamp(Camp.WHITE);

        assertThat(fakeJdbcChessGameDao.getCurrent_turn().get(0)).isEqualTo(Camp.WHITE);
    }

    @DisplayName("게임 저장 기록을 초기화한다.")
    @Test
    void reset() {
        Map<Position, Piece> board = new HashMap<>();
        board.put(Position.from("a3"), new King(Camp.BLACK));
        board.put(Position.from("c4"), new WhitePawn());
        Map<Integer, Camp> current_turn = new HashMap<>();
        current_turn.put(0, Camp.BLACK);
        FakeJdbcChessBoardDao fakeJdbcChessBoardDao = new FakeJdbcChessBoardDao(board);
        FakeJdbcChessGameDao fakeJdbcChessGameDao = new FakeJdbcChessGameDao(current_turn);
        ChessGameDBService chessGameDBService = new ChessGameDBService(fakeJdbcChessBoardDao, fakeJdbcChessGameDao);

        chessGameDBService.reset();

        assertAll(
                () -> assertThat(fakeJdbcChessBoardDao.getFakeBoard()).isEmpty(),
                () -> assertThat(fakeJdbcChessGameDao.getCurrent_turn()).isEmpty()
        );
    }
}
