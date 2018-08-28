package com.codecool.pkrupa.gameoflife.controller;

import com.codecool.pkrupa.gameoflife.model.Universe;
import com.codecool.pkrupa.gameoflife.model.UniverseFactory;
import com.codecool.pkrupa.gameoflife.model.UniverseType;
import com.codecool.pkrupa.gameoflife.view.CellDisplay;
import com.codecool.pkrupa.gameoflife.view.UniverseDisplay;

class UniverseController {

    private UniverseType type;
    private int rows;
    private int columns;
    private boolean wrapping;

    private Universe universe;
    private UniverseDisplay display;


    UniverseController() {
        createNewUniverse();
    }

    private void setUp(UniverseType type, int rows, int columns, boolean wrapping) {
        this.type = type;
        this.rows = rows;
        this.columns = columns;
        this.wrapping = wrapping;
        this.display = new UniverseDisplay(rows, columns);
    }

    Universe createNewUniverse(UniverseType type, int rows, int columns, boolean wrapping) {
        this.universe = new UniverseFactory().getUniverse(type, rows, columns, wrapping);
        setUp(type, rows, columns, wrapping);
        setUpEvents();
        return universe;
    }

    Universe createNewUniverse() {
        this.universe = new UniverseFactory().getUniverse();
        setUp(universe.getType(), universe.rowsCount(), universe.colsCount(), universe.hasWrapping());
        setUpEvents();
        return universe;
    }

    private void setUpEvents() {
        CellDisplay[][] cells = display.getCells();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int finalI = i;
                int finalJ = j;
                cells[i][j].setOnMouseClicked(event -> {
                    universe.switchCellState(finalI, finalJ);
                    display.switchCellState(finalI, finalJ);
                });
            }
        }
    }

    void endSetUp() {
        CellDisplay[][] cells = display.getCells();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j].setOnMouseClicked(null);
            }
        }
    }

    Universe resetUniverse() {
        createNewUniverse(type, rows, columns, wrapping);
        return universe;
    }

    Universe getUniverse() {
        return universe;
    }

    UniverseDisplay getDisplay() {
        return display;
    }

    void runStep() {
        display.updateDisplay(universe.runStep());
    }

}
