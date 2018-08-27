package com.codecool.pkrupa.gameoflife.model;

import com.codecool.pkrupa.gameoflife.model.simple.SimpleUniverse;

public class UniverseFactory {

    private static final UniverseType DEF_TYPE = UniverseType.SIMPLE;
    private static final int DEF_WIDTH = 80;
    private static final int DEF_HEIGHT = 50;
    private static final boolean DEF_WRAPPING = false;

    public Universe getUniverse(UniverseType type, int rows, int columns, boolean wrapping) {
        switch (type) {
            case SIMPLE:
                return new SimpleUniverse(rows, columns, wrapping);
            default:
                return new SimpleUniverse(DEF_HEIGHT, DEF_WIDTH, DEF_WRAPPING);
        }
    }

    public Universe getUniverse() {
        return getUniverse(DEF_TYPE, DEF_HEIGHT, DEF_WIDTH, DEF_WRAPPING);
    }
}
