package com.codecool.pkrupa.gameoflife.model.simple;

import com.codecool.pkrupa.gameoflife.model.Universe;
import com.codecool.pkrupa.gameoflife.model.UniverseType;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SimpleUniverse implements Universe {

    private static final String NAME = "Simple Universe";
    private final int rows;
    private final int columns;
    private Cell[][] universe;
    private Queue<Point> cellsChanged;

    private LongProperty generation;
    private LongProperty population;

    private final boolean wrapping;

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
                universe[i][j] = new Cell();
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
            if (wrapping) {
                xN = xN >= 0 ? xN % rows : rows - 1;
                yN = yN >= 0 ? yN % columns : columns - 1;
            } else {
                if ((xN < 0) || (xN > rows - 1) ||
                        (yN < 0) || (yN > columns - 1)) {
                    continue;
                }
            }
            universe[x][y].addNeighbourCell(universe[xN][yN]);
        }
    }

    private int countAliveNeighbours(int x, int y) {
        int aliveNeighbours = 0;
        List<Cell> neighbours = universe[x][y].getNeighbours();
        for (Cell neighbour : neighbours) {
            if (neighbour.isAlive()) {
                aliveNeighbours++;
            }
        }
        return aliveNeighbours;
    }

    private boolean isAliveNextGen(int x, int y) {
        int aliveNeighbours = countAliveNeighbours(x, y);
        return aliveNeighbours == 3 || (aliveNeighbours == 2 && universe[x][y].isAlive());
    }

    private void setNextState(int x, int y) {
        universe[x][y].setAliveNext(isAliveNextGen(x, y));
    }

    private void nextGeneration() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                setNextState(i, j);
            }
        }
    }

    private void updateState(int x, int y) {
        Cell cell = universe[x][y];
        if (cell.isAlive() != cell.isAliveNext()) {
            cellsChanged.add(new Point(x, y));
            int changeVal = cell.isAliveNext() ? 1 : -1;
            population.setValue(population.get() + changeVal);
        }
        cell.setAlive(cell.isAliveNext());
    }

    private void applyNextGeneration() {
        generation.setValue(generation.get() + 1);
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
    public UniverseType getType() {
        return UniverseType.SIMPLE;
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
    public boolean hasWrapping() {
        return wrapping;
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
        Cell cell = universe[x][y];
        cell.switchState();
        if (cell.isAlive()) {
            population.setValue(population.get() + 1);
        } else {
            population.setValue(population.get() - 1);
        }
    }

    @Override
    public void setCellState(int x, int y, boolean state) {
        universe[x][y].setAlive(state);
    }

    @Override
    public Queue<Point> runStep() {
        cellsChanged = new LinkedList<>();
        nextGeneration();
        applyNextGeneration();
        return cellsChanged;
    }
}
