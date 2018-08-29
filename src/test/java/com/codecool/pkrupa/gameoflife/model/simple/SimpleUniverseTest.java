package com.codecool.pkrupa.gameoflife.model.simple;

import com.codecool.pkrupa.gameoflife.model.UniverseType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleUniverseTest {
    private SimpleUniverse universe;
    private int rows;
    private int columns;
    private boolean wrapping;

    @BeforeEach
    void init() {
        rows = 10;
        columns = 15;
        wrapping = true;
        universe = new SimpleUniverse(rows, columns, wrapping);
    }

    @Test
    @DisplayName("Universe type should be SIMPLE.")
    void typeGetterTest() {
        assertEquals(UniverseType.SIMPLE, universe.getType());
    }

    @Test
    @DisplayName("Should return correct number of rows.")
    void rowsCountGetterTest() {
        assertEquals(rows, universe.rowsCount());
    }

    @Test
    @DisplayName("Should return correct number of columns.")
    void columnsCountGetterTest() {
        assertEquals(columns, universe.colsCount());
    }

    @Test
    @DisplayName("Should return correct wrapping property.")
    void wrappingPropertyGetterTest() {
        assertEquals(wrapping, universe.hasWrapping());
    }

    @Test
    @DisplayName("Initial population should be 0.")
    void initialPopulation() {
        assertEquals(0, universe.getPopulationProperty().get());
    }

    @Test
    @DisplayName("Initial generation should be 0.")
    void initialGeneration() {
        assertEquals(0, universe.getGenerationProperty().get());
    }

    @Test
    @DisplayName("After each step generation should increase by 1.")
    void generationIncreases() {
        for (int i = 0; i < 10; i++) {
            universe.runStep();
        }
        assertEquals(10, universe.getGenerationProperty().get());
    }


    @Test
    @DisplayName("Run one step of glider.")
    void oneStep() {
        Point[] initCoords = new Point[]{
                new Point(2, 2),
                new Point(2, 3),
                new Point(3, 3),
                new Point(3, 4),
                new Point(4, 2)
        };
        for (Point p : initCoords) {
            universe.switchCellState(p.x, p.y);
        }
        assertEquals(5, universe.getPopulationProperty().get(),
                "Population should equal 5.");

        LinkedList<Point> changedCells = new LinkedList<>();
        changedCells.add(new Point(2, 4));
        changedCells.add(new Point(3, 3));
        changedCells.add(new Point(4, 2));
        changedCells.add(new Point(4, 3));
        assertEquals(changedCells, universe.runStep(), "Incorrect cells changed state.");
    }
}