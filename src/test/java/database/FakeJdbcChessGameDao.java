package database;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import model.game.Camp;

public class FakeJdbcChessGameDao extends JdbcChessGameDao {

    private final Map<Integer, Camp> current_turn;
    private int index = 0;

    public FakeJdbcChessGameDao() {
        this.current_turn = new HashMap<>();
    }

    public FakeJdbcChessGameDao(Map<Integer, Camp> current_turn) {
        this.current_turn = current_turn;
        index += current_turn.size();
    }

    public void save(Camp camp) {
        current_turn.put(index, camp);
        index++;
    }

    public Optional<Camp> find() {
        if (!current_turn.isEmpty()) {
            return Optional.ofNullable(current_turn.get(index - 1));
        }
        return Optional.empty();
    }

    public void update(Camp camp) {
        for (Integer idx : current_turn.keySet()) {
            if (current_turn.get(idx) == camp.toggle()) {
                current_turn.put(idx, camp);
            }
        }
    }

    public void delete() {
        current_turn.clear();
    }

    public Map<Integer, Camp> getCurrent_turn() {
        return current_turn;
    }
}
