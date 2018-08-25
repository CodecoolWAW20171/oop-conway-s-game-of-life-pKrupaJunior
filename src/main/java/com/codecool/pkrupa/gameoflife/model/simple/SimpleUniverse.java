package com.codecool.pkrupa.gameoflife.model.simple;

import com.codecool.pkrupa.gameoflife.model.Universe;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SimpleUniverse implements Universe {

    private static final String NAME = "Simple Universe";
    private int rows;
    private int columns;
    private Cell[][] universe;
    private Queue<Point> cellsChanged;

    private LongProperty generation;
    private LongProperty population;

    private boolean wrapping;

    public SimpleUniverse(int rows, int columns, boolean wrapping) {
        this.rows = rows;
        this.columns = columns;
        this.wrapping = wrapping;
        this.universe = new Cell[rows][columns];
        initializeCells();
        setAllNeighbours();
        this.generation = new SimpleLongProperty(0);
        this.population = new SimpleLongProperty(0);
    }

    private void initializeCells() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.universe[i][j] = new Cell();
            }
        }
    }

    private void setAllNeighbours() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                addCellNeighbours(i, j);
            }
        }
    }

    private void addCellNeighbours(int x, int y) {
        for (Neighbour neighbour : Neighbour.values()) {
            int xN = x + neighbour.getDx();
            int yN = y + neighbour.getDy();
            if (this.wrapping) {
                xN = xN >= 0 ? xN % this.rows : this.rows - 1;
                yN = yN >= 0 ? yN % this.columns : this.columns - 1;
            } else {
                if ((xN < 0) || (xN > this.rows - 1) ||
                        (yN < 0) || (yN > this.columns - 1)) {
                    continue;
                }
            }
            this.universe[x][y].addNeighbourCell(this.universe[xN][yN]);
        }
    }

    private int countAliveNeighbours(int x, int y) {
        int aliveNeighbours = 0;
        List<Cell> neighbours = this.universe[x][y].getNeighbours();
        for (Cell neighbour : neighbours) {
            if (neighbour.isAlive()) {
                aliveNeighbours++;
            }
        }
        return aliveNeighbours;
    }

    private boolean isAliveNextGen(int x, int y) {
        int aliveNeighbours = countAliveNeighbours(x, y);
        return aliveNeighbours == 3 || (aliveNeighbours == 2 && this.universe[x][y].isAlive());
    }

    private void setNextState(int x, int y) {
        this.universe[x][y].setAliveNext(isAliveNextGen(x, y));
    }

    private void nextGeneration() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                setNextState(i, j);
            }
        }
    }

    private void updateState(int x, int y) {
        Cell cell = this.universe[x][y];
        if (cell.isAlive() != cell.isAliveNext()) {
            this.cellsChanged.add(new Point(x, y));
            if (cell.isAliveNext()) {
                this.population.setValue(this.population.get() + 1);
            } else {
                this.population.setValue(this.population.get() - 1);
            }
        }
        cell.setAlive(cell.isAliveNext());
    }

    private void applyNextGeneration() {
        this.generation.setValue(this.generation.get() + 1);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                updateState(i, j);
            }
        }
    }

    public static String getName() {
        return NAME;
    }

    @Override
    public int rowsCount() {
        return rows;
    }

    @Override
    public int colsCount() {
        return columns;
    }

    @Override
    public LongProperty getGenerationProperty() {
        return generation;
    }

    @Override
    public LongProperty getPopulationProperty() {
        return population;
    }

    @Override
    public void switchCellState(int x, int y) {
        Cell cell = this.universe[x][y];
        cell.switchState();
        if (cell.isAlive()) {
            this.population.setValue(this.population.get() + 1);
        } else {
            this.population.setValue(this.population.get() - 1);
        }
    }

    @Override
    public void setCellState(int x, int y, boolean state) {
        this.universe[x][y].setAlive(state);
    }

    @Override
    public void runStep() {
        this.cellsChanged = new LinkedList<>();
        nextGeneration();
        applyNextGeneration();
    }

    @Override
    public Queue<Point> getChangedCells() {
        return cellsChanged;
    }
}
