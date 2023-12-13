package org.aoc2023;

import java.util.*;

public class Day3 implements IDay {

    public static void main(String[] args) {
        new Day3().run();
    }

    @Override
    public void puzzleOne(String path) {
        char[][] input = Utils.readChar2DArrayList(path);
        int result = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                StringBuilder number = new StringBuilder();
                boolean valid = false;
                while (j < input[0].length && Character.isDigit(input[i][j])) {
                    number.append(input[i][j]);
                    valid |= isValidPart(input, i, j);
                    j++;
                }
                if (valid) result += Integer.parseInt(number.toString());
            }
        }
        System.out.println(result);
    }

    private boolean isValidPart(char[][] input, int i, int j) {
        return checkPart(input, i - 1, j)
                || checkPart(input, i + 1, j)
                || checkPart(input, i, j - 1)
                || checkPart(input, i, j + 1)
                || checkPart(input, i - 1, j - 1)
                || checkPart(input, i - 1, j + 1)
                || checkPart(input, i + 1, j - 1)
                || checkPart(input, i + 1, j + 1);

    }

    private String isValidPart2(char[][] input, int i, int j) {
        if (checkPart2(input, i - 1, j - 1)) return (i - 1) + ";" + (j - 1);
        if (checkPart2(input, i - 1, j)) return (i - 1) + ";" + j;
        if (checkPart2(input, i - 1, j + 1)) return (i - 1) + ";" + (j + 1);
        if (checkPart2(input, i, j - 1)) return i + ";" + (j - 1);
        if (checkPart2(input, i, j + 1)) return i + ";" + (j + 1);
        if (checkPart2(input, i + 1, j - 1)) return (i + 1) + ";" + (j - 1);
        if (checkPart2(input, i + 1, j)) return (i + 1) + ";" + j;
        if (checkPart2(input, i + 1, j + 1)) return (i + 1) + ";" + (j + 1);
        return null;
    }

    private boolean checkPart(char[][] input, int i, int j) {
        if (i < 0 || i >= input.length || j < 0 || j >= input[0].length) return false;
        return !Character.isDigit(input[i][j]) && input[i][j] != '.';
    }

    private boolean checkPart2(char[][] input, int i, int j) {
        if (i < 0 || i >= input.length || j < 0 || j >= input[0].length) return false;
        return input[i][j] == '*';
    }


    @Override
    public void puzzleTwo(String path) {
        char[][] input = Utils.readChar2DArrayList(path);
        Map<String, List<Integer>> stars = new HashMap<>();
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                StringBuilder number = new StringBuilder();
                String star = null;
                while (j < input[0].length && Character.isDigit(input[i][j])) {
                    number.append(input[i][j]);
                    star = (star == null) ? isValidPart2(input, i, j) : star;
                    j++;
                }
                if (star != null) {
                    if (!stars.containsKey(star)) {
                        stars.put(star, new ArrayList<>());
                    }
                    stars.get(star).add(Integer.parseInt(number.toString()));
                }
            }
        }
        System.out.println(stars.values().stream().filter(l -> l.size() > 1).map(l -> l.stream().reduce((a, b) -> a * b).orElse(0)).reduce(Integer::sum).orElse(0));
    }

}
