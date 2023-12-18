package org.aoc2023;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

interface Utils {
    static List<String> readStringList(String path) {
        try {
            return Files.readAllLines(Path.of(Objects.requireNonNull(Utils.class.getClassLoader().getResource(path)).toURI()));
        } catch (IOException | URISyntaxException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    static String readString(String path) {
        try {
            return Files.readAllLines(Path.of(Objects.requireNonNull(Utils.class.getClassLoader().getResource(path)).toURI())).get(0);
        } catch (IOException | URISyntaxException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    static List<Integer> readIntegerList(String path) {
        return readStringList(path).stream().map(Integer::parseInt).toList();
    }

    static String[][] readString2DArrayList(String path) {
        return readStringList(path).stream().map(s -> s.split("")).toArray(String[][]::new);
    }

    static char[][] readChar2DArrayList(String path) {
        return readStringList(path).stream().map(String::toCharArray).toArray(char[][]::new);
    }

    static int[][] readInt2DArrayList(String path) {
        return readStringList(path).stream().map(
                s -> Arrays.stream(s.split("")).map(Integer::parseInt).mapToInt(i -> i).toArray()
        ).toArray(int[][]::new);
    }
}