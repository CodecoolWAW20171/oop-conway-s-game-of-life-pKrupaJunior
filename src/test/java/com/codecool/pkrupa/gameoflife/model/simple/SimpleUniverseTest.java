package com.codecool.pkrupa.gameoflife.model.simple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
class SimpleUniverseTest {

    private int rows = 10;
    private int columns = 15;
    private boolean wrapping = true;


    @Test
    void rowsCountGetter() {
        int rows = 10;
        SimpleUniverse simpleUniverse = new SimpleUniverse(rows, 15, true);
        assertEquals(rows, simpleUniverse.rowsCount());
    }

    @Test
    void columnsCountGetter() {
        int columns = 15;
        SimpleUniverse simpleUniverse = new SimpleUniverse(10, columns, true);
        assertEquals(columns, simpleUniverse.colsCount());
    }

    @Test
    void initialPopulationShouldBe0() {

    }
}