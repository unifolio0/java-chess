package model.game;

public enum ChessStatus {
    INIT,
    RUNNING,
    END;

    public boolean isNotEnd() {
        return END != this;
    }
}
