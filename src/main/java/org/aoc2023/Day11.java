package org.aoc2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day11 implements IDay {

    Set<Integer> emptyRows = new HashSet<>();
    Set<Integer> emptyCols = new HashSet<>();
    List<int[]> galaxies = new ArrayList<>();

    public static void main(String[] args) {
        new Day11().run();
    }

    @Override
    public void puzzleOne(String path) {
        System.out.println(getResult(1L));
    }

    private long getResult(Long ext) {
        long result = 0L;
        Set<String> pairs = new HashSet<>();
        for (int[] g1 : galaxies) {
            for (int[] g2 : galaxies) {
                String key = String.format("{%s:%s}-{%s:%s}", g1[0], g1[1], g2[0], g2[1]);
                String keyM = String.format("{%s:%s}-{%s:%s}", g2[0], g2[1], g1[0], g1[1]);
                if (!pairs.contains(key) && !pairs.contains(keyM) && !(g1[0] + ":" + g1[1]).equals(g2[0] + ":" + g2[1])) {
                    long addRows = emptyRows.stream()
                            .filter(row -> Math.min(g1[0], g2[0]) < row && Math.max(g1[0], g2[0]) > row).count() * ext;
                    long addCols = emptyCols.stream().filter(col -> Math.min(g1[1], g2[1]) < col && Math.max(g1[1], g2[1]) > col).count() * ext;
                    long diff = ((Math.abs(g1[0] - g2[0]) + addRows) + (Math.abs(g1[1] - g2[1]) + addCols));
                    result = result + diff;
                    pairs.add(key);
                }
            }
        }
        return result;
    }

    @Override
    public void initData(String path) {
        final char[][] input = Utils.readChar2DArrayList(path);
        for (int i = 0; i < input.length; i++) {
            boolean emptyRow = true;
            for (int j = 0; j < input[0].length; j++) {
                if (input[i][j] != '.') {
                    emptyRow = false;
                    break;
                }
            }
            if (emptyRow) emptyRows.add(i);
        }
        for (int j = 0; j < input[0].length; j++) {
            boolean emptyCol = true;
            for (char[] chars : input) {
                if (chars[j] != '.') {
                    emptyCol = false;
                    break;
                }
            }
            if (emptyCol) emptyCols.add(j);
        }
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                if (input[i][j] == '#') galaxies.add(new int[]{i, j});
            }
        }
    }

    @Override
    public void puzzleTwo(String path) {
        System.out.println(getResult(999999L));
    }

}
