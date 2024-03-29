package database;

import dto.ChessGameDto;
import model.game.Camp;
import model.game.ChessBoard;
import model.piece.Piece;
import model.position.Moving;
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

    private void savePiece(Position position, Piece piece) {
        chessBoardDao.save(position, piece);
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
        chessBoardDao.update(position, piece);
    }

    private void deletePiece(Position position) {
        chessBoardDao.delete(position);
    }

    public void updateCamp(Camp camp) {
        chessGameDao.update(camp);
    }

    public void reset() {
        chessBoardDao.deleteAll();
        chessGameDao.delete();
    }
}
