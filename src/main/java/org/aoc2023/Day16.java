package org.aoc2023;

import java.util.HashSet;
import java.util.Set;

public class Day16 implements IDay {

    public static void main(String[] args) {
        new Day16().runTwo();
    }

    @Override
    public void puzzleOne(String path) {
        System.out.println(calculate(Utils.readChar2DArrayList(path), -1, 0, 'r'));
    }

    private void moveBeam(Set<String> energized, char[][] layout, int x, int y, char direction) {
        if (x != -1 && y != -1 && x != layout.length && y != layout.length) {
            if (!energized.add(x + ";" + y + "->" + direction)) return;
        }
        if (direction == 'r' && x < layout.length - 1) {
            int to = x + 1;
            if (layout[y][to] == '|') {
                moveBeam(energized, layout, to, y, 'u');
                moveBeam(energized, layout, to, y, 'd');
            } else if (layout[y][to] == '\\') {
                moveBeam(energized, layout, to, y, 'd');
            } else if (layout[y][to] == '/') {
                moveBeam(energized, layout, to, y, 'u');
            } else {
                moveBeam(energized, layout, to, y, 'r');
            }
        }

        if (direction == 'l' && x > 0) {
            int to = x - 1;
            if (layout[y][to] == '|') {
                moveBeam(energized, layout, to, y, 'u');
                moveBeam(energized, layout, to, y, 'd');
            } else if (layout[y][to] == '\\') {
                moveBeam(energized, layout, to, y, 'u');
            } else if (layout[y][to] == '/') {
                moveBeam(energized, layout, to, y, 'd');
            } else {
                moveBeam(energized, layout, to, y, 'l');
            }
        }

        if (direction == 'u' && y > 0) {
            int to = y - 1;
            if (layout[to][x] == '-') {
                moveBeam(energized, layout, x, to, 'l');
                moveBeam(energized, layout, x, to, 'r');
            } else if (layout[to][x] == '\\') {
                moveBeam(energized, layout, x, to, 'l');
            } else if (layout[to][x] == '/') {
                moveBeam(energized, layout, x, to, 'r');
            } else {
                moveBeam(energized, layout, x, to, 'u');
            }
        }

        if (direction == 'd' && y < layout.length - 1) {
            int to = y + 1;
            if (layout[to][x] == '-') {
                moveBeam(energized, layout, x, to, 'l');
                moveBeam(energized, layout, x, to, 'r');
            } else if (layout[to][x] == '\\') {
                moveBeam(energized, layout, x, to, 'r');
            } else if (layout[to][x] == '/') {
                moveBeam(energized, layout, x, to, 'l');
            } else {
                moveBeam(energized, layout, x, to, 'd');
            }
        }

    }

    @Override
    public void puzzleTwo(String path) {
        char[][] layout = Utils.readChar2DArrayList(path);
        int max = 0;

        for (int j = 0; j < layout.length; j++) {
            max = Math.max(max, calculate(layout, j, -1, 'd'));
        }
        for (int j = 0; j < layout.length; j++) {
            max = Math.max(max, calculate(layout, j, layout.length, 'u'));
        }
        for (int i = 1; i < layout.length - 1; i++) {
            max = Math.max(max, calculate(layout, -1, i, 'r'));
        }
        for (int i = 1; i < layout.length - 1; i++) {
            max = Math.max(max, calculate(layout, layout.length, i, 'l'));
        }
        System.out.println(max);
    }

    private int calculate(char[][] layout, int x, int y, char direction) {
        Set<String> energized = new HashSet<>();
        moveBeam(energized, layout, x, y, direction);
        return energized.stream().map(s -> s.split("->")[0]).distinct().toList().size();
    }

}
