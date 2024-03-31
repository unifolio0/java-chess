package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import model.game.Camp;

public class JdbcChessGameDao {

    private static final String TABLE = "chessgame";

    public void save(Camp camp) {
        final String query = "INSERT INTO " + TABLE + " VALUES(?)";
        try (final Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, camp.name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Camp> find() {
        final String query = "SELECT * FROM " + TABLE;
        try (final Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(Camp.valueOf(resultSet.getString("current_turn")));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public void update(Camp camp) {
        final String query =
                "UPDATE " + TABLE + " SET current_turn = ? WHERE current_turn = ?";
        try (final Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, camp.name());
            preparedStatement.setString(2, camp.toggle().name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete() {
        final String query = "DELETE FROM " + TABLE;
        try (final Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
