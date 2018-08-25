package com.codecool.pkrupa.gameoflife.view;

import javafx.scene.paint.Color;

public enum CellDisplayState {
    ALIVE(Color.BLUE),
    DEAD(Color.WHITE);

    private Color color;

    CellDisplayState(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
