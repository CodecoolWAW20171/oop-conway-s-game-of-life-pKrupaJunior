package com.codecool.pkrupa.gameoflife.model;

import javafx.beans.property.LongProperty;

import java.awt.*;
import java.util.Queue;

public interface Universe {
    int rowsCount();

    int colsCount();

    LongProperty getGenerationProperty();

    LongProperty getPopulationProperty();

    void switchCellState(int x, int y);

    void setCellState(int x, int y, boolean state);

    Queue<Point> runStep();
}
