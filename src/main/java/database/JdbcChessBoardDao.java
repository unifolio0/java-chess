package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import model.piece.Piece;
import model.piece.PieceGenerator;
import model.position.Position;

public class JdbcChessBoardDao {

    private static final String TABLE = "chessboard";

    public void saveAll(Map<Position, Piece> board) {
        board.forEach(this::save);
    }

    public void save(Position position, Piece piece) {
        final String query = "INSERT INTO " + TABLE + " VALUES(?, ?, ?, ?)";
        try (final Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, position.getColumn().getValue());
            preparedStatement.setString(2, position.getRow().getValue());
            preparedStatement.setString(3, piece.getPieceType().name());
            preparedStatement.setString(4, piece.getCamp().name());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Position, Piece> findAll() {
        final String query = "SELECT * FROM " + TABLE;
        try (final Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            Map<Position, Piece> board = new HashMap<>();
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                board.put(Position.from(resultSet.getString("board_column") + resultSet.getString("board_row")),
                        PieceGenerator.getPiece(resultSet.getString("piece_type") + "_" + resultSet.getString("camp")));
            }
            return board;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Position position, Piece piece) {
        final String query =
                "UPDATE " + TABLE + " SET piece_type = ?, camp = ? WHERE board_column = ? AND board_row = ?";
        try (final Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, piece.getPieceType().name());
            preparedStatement.setString(2, piece.getCamp().name());
            preparedStatement.setString(3, position.getColumn().getValue());
            preparedStatement.setString(4, position.getRow().getValue());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() {
        final String query = "DELETE FROM " + TABLE;
        try (final Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Position position) {
        final String query = "DELETE FROM " + TABLE + " WHERE board_column = ? AND board_row = ?";
        try (final Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, position.getColumn().getValue());
            preparedStatement.setString(2, position.getRow().getValue());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
