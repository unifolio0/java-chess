package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import model.game.ChessBoard;
import model.piece.Piece;
import model.piece.PieceGenerator;
import model.position.Position;

public class JdbcChessBoardDao {

    private static final String TABLE = "chessboard";

    public void saveAll(Map<Position, Piece> board) {
        final String query = "INSERT INTO " + TABLE + " VALUES(?, ?, ?, ?)";
        try (final Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (Entry<Position, Piece> enetry : board.entrySet()) {
                preparedStatement.setString(1, enetry.getKey().getColumn().getValue());
                preparedStatement.setString(2, enetry.getKey().getRow().getValue());
                preparedStatement.setString(3, enetry.getValue().getPieceType().name());
                preparedStatement.setString(4, enetry.getValue().getCamp().name());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ChessBoard findAll() {
        final String query = "SELECT * FROM " + TABLE;
        try (final Connection connection = DBConnection.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            Map<Position, Piece> board = new HashMap<>();
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                board.put(Position.from(resultSet.getString("board_column") + resultSet.getString("board_row")),
                        PieceGenerator.getPiece(resultSet.getString("piece_type") + "_" + resultSet.getString("camp")));
            }
            return new ChessBoard(board);
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
}
