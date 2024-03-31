package database;

import java.util.HashMap;
import java.util.Map;
import model.piece.Piece;
import model.position.Position;

public class FakeJdbcChessBoardDao extends JdbcChessBoardDao {

    private final Map<Position, Piece> fakeBoard;

    public FakeJdbcChessBoardDao() {
        this.fakeBoard = new HashMap<>();
    }

    public FakeJdbcChessBoardDao(Map<Position, Piece> board) {
        this.fakeBoard = new HashMap<>();
        for (Position pos : board.keySet()) {
            fakeBoard.put(pos, board.get(pos));
        }
    }

    public void saveAll(Map<Position, Piece> board) {
        board.forEach(this::save);
    }

    public void save(Position position, Piece piece) {
        fakeBoard.put(position, piece);
    }

    public Map<Position, Piece> findAll() {
        Map<Position, Piece> board = new HashMap<>();
        for (Position pos : fakeBoard.keySet()) {
            board.put(pos, fakeBoard.get(pos));
        }
        return board;
    }

    public void update(Position position, Piece piece) {
        for (Position pos : fakeBoard.keySet()) {
            if (pos.equals(position)) {
                fakeBoard.put(pos, piece);
            }
        }
    }

    public void deleteAll() {
        fakeBoard.clear();
    }

    public void delete(Position position) {
        for (Position pos : fakeBoard.keySet()) {
            if (pos.equals(position)) {
                fakeBoard.remove(pos);
            }
        }
    }

    public Map<Position, Piece> getFakeBoard() {
        return fakeBoard;
    }
}
