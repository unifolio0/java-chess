package model.game;

public enum Camp {

    BLACK,
    WHITE;

    public Camp toggle() {
        if (this == BLACK) {
            return WHITE;
        }
        return BLACK;
    }
}
