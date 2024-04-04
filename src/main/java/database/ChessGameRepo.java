package database;

import dto.GameBoardDto;
import model.game.ChessGame;

public class ChessGameRepo {

    private final JdbcChessBoardDao jdbcChessBoardDao;
    private final JdbcChessGameDao jdbcChessGameDao;

    public ChessGameRepo() {
        this.jdbcChessBoardDao = new JdbcChessBoardDao();
        this.jdbcChessGameDao = new JdbcChessGameDao();
    }

    public ChessGameRepo(JdbcChessBoardDao jdbcChessBoardDao, JdbcChessGameDao jdbcChessGameDao) {
        this.jdbcChessBoardDao = jdbcChessBoardDao;
        this.jdbcChessGameDao = jdbcChessGameDao;
    }

    public ChessGame reload() {
        GameBoardDto d = GameBoardDto.from(jdbcChessBoardDao.findAll().getBoard());
        return new ChessGame(jdbcChessBoardDao.findAll(), jdbcChessGameDao.findCamp().get(), jdbcChessGameDao.findStatus().get());
    }

    public boolean isContinue() {
        return jdbcChessGameDao.findCamp().isPresent();
    }

    public void saveAll(ChessGame chessGame) {
        jdbcChessBoardDao.saveAll(chessGame.getBoard());
        jdbcChessGameDao.save(chessGame.getCamp(), chessGame.getChessStatus());
    }

    public void reset() {
        jdbcChessBoardDao.deleteAll();
        jdbcChessGameDao.delete();
    }

    public void updateGame(ChessGame chessGame) {
        reset();
        saveAll(chessGame);
    }
}
