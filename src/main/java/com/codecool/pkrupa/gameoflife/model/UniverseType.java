package com.codecool.pkrupa.gameoflife.model;

import com.codecool.pkrupa.gameoflife.model.simple.SimpleUniverse;

public enum UniverseType {
    SIMPLE(SimpleUniverse.getName()),
    THREE_POINT("3-pointer"),
    HASHLIFE("Hashlife");

    private final String name;

    UniverseType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
