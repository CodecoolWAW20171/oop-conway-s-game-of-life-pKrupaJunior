package com.codecool.pkrupa.gameoflife.controller;

import com.codecool.pkrupa.gameoflife.model.Universe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RleReader {
    private static final String SIZE_PATTERN = "^x = \\d+, y = \\d+";
    private static final String LINE_END = "\\$";
    private static final String FILE_END = "!";
    private static final String ALIVE_CELL = "o";
    private static final String DEAD_CELL = "b";

    private String fileLocation;
    private boolean[][] cells;
    private int width;
    private int height;

    public RleReader(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public void load() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream(fileLocation)));
        setSize(br);
        initCells();
        String[] rows = readRows(br);
        setCells(rows);
    }

    private void initCells() {
        cells = new boolean[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = false;
            }
        }
    }

    private void setSize(BufferedReader reader) throws IOException {
        // Find line with dimensions
        String line;
        do {
            line = reader.readLine();
            if (line == null) {
                throw new IOException("Could not find size (x = m, y = n)");
            }
        } while (!line.matches(SIZE_PATTERN));
        // Set size
        String[] size = line.replaceAll("[^0-9,]+", "").split(",");
        width = Integer.parseInt(size[0]);
        height = Integer.parseInt(size[1]);
    }

    private String[] readRows(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        do {
            line = reader.readLine();
            if (line == null) {
                throw new IOException("Invalid file format. No file end \"!\".");
            }
            sb.append(line);
        } while (!line.contains(FILE_END));
        return sb.toString().split(LINE_END);
    }

    private void setCells(String[] rows) {
        // run in form of <run_count><tag> e.g. "4b"
        Pattern pattern = Pattern.compile("\\d*+[a-z]");

        // Iterate through rows
        int rowsCount = rows.length;
        for (int r = 0; r < rowsCount; r++) {

            Matcher matcher = pattern.matcher(rows[r]);
            int c = 0;  // column number

            // Find runs
            while (matcher.find()) {
                String run = matcher.group();

                // Set run_count to 1 if not explicitly stated
                int count = run.length() > 1 ?
                        Integer.parseInt(run.substring(0, run.length() - 1)) : 1;
                String tag = run.substring(run.length() - 1);

                // Iterate through cells in row and set proper cell state
                for (int i = 0; i < count; i++) {
                    cells[r][c++] = tag.equals(ALIVE_CELL);
                }
            }
        }
    }

    public void toUniverse(Universe universe, int xS, int yS) {

        // Check if universe is large enough to fit the pattern
        if ((universe.rowsCount() < (xS + height)) ||
                (universe.colsCount() < (yS + width))) {
            throw new ArrayIndexOutOfBoundsException("Universe is too small for this pattern.");
        }

        // Place pattern in the universe
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                universe.setCellState(xS + i, yS + j, cells[i][j]);
            }
        }
    }

    public boolean[][] getCells() {
        return cells;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
