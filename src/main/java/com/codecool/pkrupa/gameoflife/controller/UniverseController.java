package com.codecool.pkrupa.gameoflife.controller;

import com.codecool.pkrupa.gameoflife.model.Universe;
import com.codecool.pkrupa.gameoflife.model.UniverseFactory;
import com.codecool.pkrupa.gameoflife.model.UniverseType;
import com.codecool.pkrupa.gameoflife.view.CellDisplay;
import com.codecool.pkrupa.gameoflife.view.UniverseDisplay;
import javafx.scene.layout.Pane;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

public class UniverseController {

    private final UniverseType type;
    private final int rows;
    private final int columns;
    private final boolean wrapping;

    private Universe universe;
    private UniverseDisplay display;

    public UniverseController(Universe universe) {
        this.rows = universe.rowsCount();
        this.columns = universe.colsCount();
        this.wrapping = universe.hasWrapping();
        this.type = universe.getType();
        this.universe = universe;
        this.display = new UniverseDisplay(rows, columns);
        setUpEvents();
    }

    public Universe getUniverse() {
        return universe;
    }

    public UniverseDisplay getDisplay() {
        return display;
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

    public void endSetUp() {
        CellDisplay[][] cells = display.getCells();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j].setOnMouseClicked(null);
            }
        }
    }

    public void runStep() {
        display.updateDisplay(universe.runStep());
    }

}
