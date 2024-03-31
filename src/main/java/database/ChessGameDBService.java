package database;

import dto.ChessGameDto;
import model.game.Camp;
import model.game.ChessBoard;
import model.piece.Piece;
import model.position.Moving;
import model.position.Position;

public class ChessGameDBService {

    private final JdbcChessBoardDao jdbcChessBoardDao;
    private final JdbcChessGameDao jdbcChessGameDao;

    public ChessGameDBService() {
        this.jdbcChessBoardDao = new JdbcChessBoardDao();
        this.jdbcChessGameDao = new JdbcChessGameDao();
    }

    public ChessGameDBService(JdbcChessBoardDao jdbcChessBoardDao, JdbcChessGameDao jdbcChessGameDao) {
        this.jdbcChessBoardDao = jdbcChessBoardDao;
        this.jdbcChessGameDao = jdbcChessGameDao;
    }

    public ChessGameDto reload() {
        return new ChessGameDto(jdbcChessBoardDao.findAll(), jdbcChessGameDao.find().get());
    }

    public boolean isContinue() {
        return jdbcChessGameDao.find().isPresent();
    }

    public void saveAll(ChessGameDto chessGameDto) {
        jdbcChessBoardDao.saveAll(chessGameDto.board());
        jdbcChessGameDao.save(chessGameDto.camp());
    }

    public void moveUpdate(ChessBoard chessBoard, Moving moving) {
        Piece source = chessBoard.findPiece(moving.getCurrentPosition());
        if (chessBoard.isExistPosition(moving.getNextPosition())) {
            updatePiece(moving.getNextPosition(), source);
        } else {
            savePiece(moving.getNextPosition(), source);
        }
        deletePiece(moving.getCurrentPosition());
    }

    private void updatePiece(Position position, Piece piece) {
        jdbcChessBoardDao.update(position, piece);
    }

    private void savePiece(Position position, Piece piece) {
        jdbcChessBoardDao.save(position, piece);
    }

    private void deletePiece(Position position) {
        jdbcChessBoardDao.delete(position);
    }

    public void updateCamp(Camp camp) {
        jdbcChessGameDao.update(camp);
    }

    public void reset() {
        jdbcChessBoardDao.deleteAll();
        jdbcChessGameDao.delete();
    }
}
