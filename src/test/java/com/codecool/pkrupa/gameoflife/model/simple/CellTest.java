package com.codecool.pkrupa.gameoflife.model.simple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    private Cell cell;

    @BeforeEach
    void init() {
        cell = new Cell();
    }

    @Test
    @DisplayName("New cell is dead.")
    void isAliveTest() {
        assertFalse(cell.isAlive(), "New cell should be dead.");
    }

    @Test
    @DisplayName("Cell can be set alive and dead.")
    void setAliveTest() {
        cell.setAlive(true);
        assertTrue(cell.isAlive(), "Cell should alive after setting alive true.");
        cell.setAlive(false);
        assertFalse(cell.isAlive(), "Cell should dead after setting alive false.");
    }

    @Test
    @DisplayName("Cell state can be switched back and forth.")
    void switchStateTest() {
        cell.switchState();
        assertTrue(cell.isAlive(), "Switching dead cell should make it alive.");
        cell.switchState();
        assertFalse(cell.isAlive(), "Switching alive cell should make it dead.");
    }

    @Test
    @DisplayName("Cell can be marked as alive or dead next generation.")
    void aliveNextTest() {
        cell.setAliveNext(false);
        assertFalse(cell.isAliveNext(), "Should be dead next after setting aliveNext false.");
        cell.setAliveNext(true);
        assertTrue(cell.isAliveNext(), "Should be alive next after setting aliveNext true.");
    }

    @Test
    @DisplayName("Can add and get neighbour cells.")
    void neighbourCellAdderAndGetterTest() {
        assertEquals(0, cell.getNeighbours().size(),
                "New cell should have no neighbours.");
        Cell neighbour = new Cell();
        cell.addNeighbourCell(neighbour);
        assertEquals(1, cell.getNeighbours().size(),
                "Should return one neighbour after adding a single neighbour to new cell.");
        assertEquals(neighbour, cell.getNeighbours().get(0),
                "Get neighbour returns the exact neighbour after adding it.");
    }

}