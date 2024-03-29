package database;

import dto.ChessGameDto;
import model.Camp;
import model.piece.Piece;
import model.position.Position;

public class ChessGameDBService {

    private final ChessBoardDao chessBoardDao;
    private final ChessGameDao chessGameDao;

    public ChessGameDBService() {
        this.chessBoardDao = new ChessBoardDao();
        this.chessGameDao = new ChessGameDao();
    }

    public ChessGameDto reload() {
        return new ChessGameDto(chessBoardDao.findAll(), chessGameDao.find());
    }

    public boolean isContinue() {
        return chessGameDao.find().describeConstable().isPresent();
    }

    public void saveAll(ChessGameDto chessGameDto) {
        chessBoardDao.saveAll(chessGameDto.board());
        chessGameDao.save(chessGameDto.camp());
    }

    public void savePiece(Position position, Piece piece) {
        chessBoardDao.save(position, piece);
    }

    public void updatePiece(Position position, Piece piece) {
        chessBoardDao.update(position, piece);
    }

    public void updateCamp(Camp camp) {
        chessGameDao.update(camp);
    }

    public void deletePiece(Position position) {
        chessBoardDao.delete(position);
    }

    public void reset() {
        chessBoardDao.deleteAll();
        chessGameDao.delete();
    }
}
