package com.codecool.pkrupa.gameoflife.model.simple;

import java.util.LinkedList;
import java.util.List;

class Cell {
    private boolean alive;
    private boolean aliveNext;
    private List<Cell> neighbours;

    Cell() {
        this.alive = false;
        this.neighbours = new LinkedList<>();
    }

    boolean isAlive() {
        return alive;
    }

    void setAlive(boolean alive) {
        this.alive = alive;
    }

    void switchState() {
        this.alive = !this.alive;
    }

    boolean isAliveNext() {
        return aliveNext;
    }

    void setAliveNext(boolean aliveNext) {
        this.aliveNext = aliveNext;
    }

    void addNeighbourCell(Cell neighbour) {
        this.neighbours.add(neighbour);
    }

    List<Cell> getNeighbours() {
        return neighbours;
    }
}
