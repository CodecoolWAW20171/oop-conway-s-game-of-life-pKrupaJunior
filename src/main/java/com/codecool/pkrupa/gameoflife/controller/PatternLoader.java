package com.codecool.pkrupa.gameoflife.controller;

import com.codecool.pkrupa.gameoflife.model.Universe;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PatternLoader {
    private static final Map<String, String> PATTERNS = new HashMap<>();

    private Universe universe;

    public PatternLoader(Universe universe) {
        this.universe = universe;
    }

    public void loadMouseover() {

    }

    public int[] getClosestCell(double x, double y) {

        return new int[] {0, 0};
    }
}
