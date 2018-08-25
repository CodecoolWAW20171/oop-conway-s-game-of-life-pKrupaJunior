package com.codecool.pkrupa.gameoflife.model.simple;

public enum Neighbour {
    NW (-1, -1),
    N (-1, 0),
    NE (-1, 1),
    W (0, -1),
    E (0, 1),
    SW (1, -1),
    S (1, 0),
    SE (1, 1);

    private int dx; // row
    private int dy; // column

    Neighbour(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
