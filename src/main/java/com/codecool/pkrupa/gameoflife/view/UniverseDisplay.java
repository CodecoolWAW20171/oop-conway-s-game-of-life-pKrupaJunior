package com.codecool.pkrupa.gameoflife.view;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.util.Queue;

public class UniverseDisplay extends GridPane {

    private int rows;
    private int columns;
    private CellDisplay[][] cells;

    public UniverseDisplay(int rows, int columns) {
        super();
        this.setHgap(1);
        this.setVgap(1);
        this.setGridLinesVisible(true);
        this.setPadding(new Insets(10));

        this.rows = rows;
        this.columns = columns;
        this.cells = new CellDisplay[rows][columns];
        setUpGrid();
    }

    private void setUpGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                CellDisplay cellDisplay = new CellDisplay();
                cells[i][j] = cellDisplay;
                this.add(cellDisplay, j, i);
            }
        }
    }

    public void updateDisplay(Queue<Point> changedCells) {
        while (!changedCells.isEmpty()) {
            Point cell = changedCells.poll();
            cells[cell.x][cell.y].switchState();
        }
    }

    public CellDisplay[][] getCells() {
        return cells;
    }

    public CellDisplay getCell(int x, int y) {
        return cells[x][y];
    }

    public void switchCellState(int x, int y) {
        cells[x][y].switchState();
    }
}
