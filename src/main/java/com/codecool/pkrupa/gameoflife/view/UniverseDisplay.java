package com.codecool.pkrupa.gameoflife.view;

import com.codecool.pkrupa.gameoflife.model.Universe;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.util.HashMap;
import java.util.Queue;

public class UniverseDisplay extends GridPane {

    private int rows;
    private int columns;
    private Universe universe;
    private CellDisplay[][] cells;

    public UniverseDisplay(Universe universe) {
        super();
        this.setHgap(1);
        this.setVgap(1);
        this.setGridLinesVisible(true);
        this.setPadding(new Insets(10));

        this.universe = universe;
        this.rows = universe.rowsCount();
        this.columns = universe.colsCount();
        this.cells = new CellDisplay[rows][columns];
    }

    public void setUpGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                CellDisplay cellDisplay = new CellDisplay();
                cells[i][j] = cellDisplay;
                int finalI = i;
                int finalJ = j;
                cellDisplay.setOnMouseClicked(event -> {
                    cellDisplay.switchState();
                    universe.switchCellState(finalI, finalJ);
                });
                this.add(cellDisplay, j, i);
            }
        }
    }

    public void endSetUp() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells[i][j].setOnMouseClicked(null);
            }
        }
    }

    public void updateDisplay() {
        Queue<Point> changedCells = this.universe.getChangedCells();
        while (!changedCells.isEmpty()) {
            Point cell = changedCells.poll();
            this.cells[cell.x][cell.y].switchState();
        }
    }

}
