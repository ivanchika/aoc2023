package org.aoc2023;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Day14 implements IDay {

    public static void main(String[] args) {
        new Day14().runTwo();
    }

    @Override
    public void puzzleOne(String path) {
        char[][] stones = Utils.readChar2DArrayList(path);
        System.out.println(
                IntStream.range(0, stones.length).map(j -> {
                            int rock = stones.length + 1;
                            int result = 0;
                            for (int i = 0; i < stones.length; i++) {
                                if (stones[i][j] == 'O') result += --rock;
                                if (stones[i][j] == '#') rock = stones.length - i;
                            }
                            return result;
                        })
                        .reduce(Integer::sum)
                        .orElse(0));
    }

    @Override
    public void puzzleTwo(String path) {
        Map<Integer, Long> hashes = new HashMap<>();
        char[][] stones = Utils.readChar2DArrayList(path);
//        printArray(stones);
        long steps = 1000000000L;
        boolean repeat = false;
        for (long step = 1; step <= steps; step++) {
            north(stones);
            west(stones);
            south(stones);
            east(stones);
            int hash = Arrays.deepHashCode(stones);
            if (!repeat) {
                if (hashes.containsKey(hash)) {
                    long prev = hashes.get(hash);
                    long diff = step - prev;
                    long multi = (steps - prev) / diff;
                    step = prev + (multi * diff);
                    repeat = true;
                } else {
                    hashes.put(hash, step);
                }
            }
        }

        System.out.println(
                IntStream.range(0, stones.length).map(j ->
                                IntStream.range(0, stones.length).map(i ->
                                        stones[i][j] == 'O' ? stones.length - i : 0
                                ).reduce(Integer::sum).orElse(0))
                        .reduce(Integer::sum)
                        .orElse(0));
    }

    private void north(char[][] stones) {
        for (int j = 0; j < stones.length; j++) {
            int rock = -1;
            for (int i = 0; i < stones.length; i++) {
                if (stones[i][j] == 'O') {
                    if (i > rock + 1) {
                        stones[rock + 1][j] = 'O';
                        stones[i][j] = '.';
                    }
                    rock++;
                }
                if (stones[i][j] == '#') {
                    rock = i;
                }
            }
        }
    }

    private void west(char[][] stones) {
        for (int i = 0; i < stones.length; i++) {
            int rock = -1;
            for (int j = 0; j < stones.length; j++) {
                if (stones[i][j] == 'O') {
                    if (j > rock + 1) {
                        stones[i][rock + 1] = 'O';
                        stones[i][j] = '.';
                    }
                    rock++;
                }
                if (stones[i][j] == '#') {
                    rock = j;
                }
            }
        }
    }

    private void south(char[][] stones) {
        for (int j = 0; j < stones.length; j++) {
            int rock = stones.length;
            for (int i = stones.length - 1; i >= 0; i--) {
                if (stones[i][j] == 'O') {
                    if (i < rock - 1) {
                        stones[rock - 1][j] = 'O';
                        stones[i][j] = '.';
                    }
                    rock--;
                }
                if (stones[i][j] == '#') {
                    rock = i;
                }
            }
        }
    }

    private void east(char[][] stones) {
        for (int i = 0; i < stones.length; i++) {
            int rock = stones.length;
            for (int j = stones.length - 1; j >= 0; j--) {
                if (stones[i][j] == 'O') {
                    if (j < rock - 1) {
                        stones[i][rock - 1] = 'O';
                        stones[i][j] = '.';
                    }
                    rock--;
                }
                if (stones[i][j] == '#') {
                    rock = j;
                }
            }
        }
    }

    private static void printArray(char[][] array) {
        System.out.println("------------------------");
        Arrays.stream(array).forEach(System.out::println);
        System.out.println("------------------------");
    }
}
