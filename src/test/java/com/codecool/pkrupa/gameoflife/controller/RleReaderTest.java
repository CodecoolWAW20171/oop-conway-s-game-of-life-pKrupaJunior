package com.codecool.pkrupa.gameoflife.controller;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RleReaderTest {

    @TestFactory
    @DisplayName("Load glider from rle file.")
    List<DynamicTest> loaderTest() throws IOException {
        RleReader reader = new RleReader("/glider.rle");
        reader.load();
        // Expected results
        int width = 3;
        int height = 3;
        boolean[][] cells = new boolean[][]{
                {false, true, false},
                {false, false, true},
                {true, true, true}
        };
        return Arrays.asList(
                DynamicTest.dynamicTest("Width should equal " + width, () ->
                        assertEquals(width, reader.getWidth())),
                DynamicTest.dynamicTest("Height should equal " + height, () ->
                        assertEquals(height, reader.getHeight())),
                DynamicTest.dynamicTest("Check cell state", () ->
                        assertArrayEquals(cells, reader.getCells()))
        );
    }

}