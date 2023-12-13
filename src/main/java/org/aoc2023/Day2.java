package org.aoc2023;

import java.util.Arrays;
import java.util.List;

public class Day2 implements IDay {

    public static void main(String[] args) {
        new Day2().run();
    }

    @Override
    public void puzzleOne(String path) {
        int redsMax = 12;
        int greensMax = 13;
        int bluesMax = 14;
        final List<String> lines = Utils.readStringList(path);
        System.out.println(lines.stream().map(
                        line -> {
                            final String[] split = line.split(": ");
                            int game = Integer.parseInt(split[0].replace("Game ", ""));
                            return Boolean.TRUE.equals((Arrays.stream(split[1]
                                            .split("; "))
                                    .flatMap(s -> Arrays.stream(s.split(", ")))
                                    .map(s ->
                                            (s.endsWith("red") && Integer.parseInt(s.split(" ")[0]) <= redsMax)
                                                    || (s.endsWith("green") && Integer.parseInt(s.split(" ")[0]) <= greensMax)
                                                    || (s.endsWith("blue") && Integer.parseInt(s.split(" ")[0]) <= bluesMax)
                                    ).reduce(Boolean::logicalAnd).orElse(false))) ? game : 0;
                        })
                .reduce(Integer::sum)
                .orElse(0));
    }

    @Override
    public void puzzleTwo(String path) {
        final List<String> lines = Utils.readStringList(path);
        System.out.println(lines.stream().map(
                        line -> {
                            List<String> strings = Arrays.stream(line.split(": ")[1].split("; "))
                                    .flatMap(s -> Arrays.stream(s.split(", "))).toList();
                            int maxRed = strings.stream().filter(s -> s.contains("red")).map(s -> Integer.parseInt(s.split(" ")[0])).max(Integer::compareTo).orElse(0);
                            int maxGreen = strings.stream().filter(s -> s.contains("green")).map(s -> Integer.parseInt(s.split(" ")[0])).max(Integer::compareTo).orElse(0);
                            int maxBlue = strings.stream().filter(s -> s.contains("blue")).map(s -> Integer.parseInt(s.split(" ")[0])).max(Integer::compareTo).orElse(0);
                            return maxRed * maxGreen * maxBlue;
                        })
                .reduce(Integer::sum)
                .orElse(0));
    }

}
