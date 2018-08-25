package com.codecool.pkrupa.gameoflife.view;

import javafx.scene.shape.Rectangle;

public class CellDisplay extends Rectangle {
    public static final int SIZE = 10;

    private CellDisplayState state;

    public CellDisplay() {
        super(SIZE, SIZE, CellDisplayState.DEAD.getColor());
        this.state = CellDisplayState.DEAD;
    }

    void switchState() {
        switch (this.state) {
            case DEAD:
                this.state = CellDisplayState.ALIVE;
                break;
            case ALIVE:
                this.state = CellDisplayState.DEAD;
                break;
        }
        this.setFill(this.state.getColor());
    }
}
