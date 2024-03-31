package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public Camp find() {
        final String query = "SELECT * FROM " + TABLE;
        try (final Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                resultSet.close();
                return Camp.valueOf(resultSet.getString("current_turn"));
            }
            resultSet.close();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("저장된 기록이 없습니다.");
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
