package org.aoc2023;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day6 implements IDay {
    public static void main(String[] args) {
        new Day6().run();
    }

    @Override
    public void puzzleOne(String path) {
        final List<String> input = Utils.readStringList(path);
        final List<Integer> times = Arrays.stream(input.get(0).substring(11).split(" ")).map(String::trim).filter(s -> !s.isBlank()).map(Integer::parseInt).toList();
        final List<Integer> distances = Arrays.stream(input.get(1).substring(11).split(" ")).map(String::trim).filter(s -> !s.isBlank()).map(Integer::parseInt).toList();
        System.out.println(
                IntStream.range(0, times.size())
                        .mapToObj(i -> IntStream.range(1, times.get(i))
                                .filter(press -> (press * (times.get(i) - press)) > distances.get(i))
                                .count())
                        .reduce((n1, n2) -> n1 * n2).orElse(0L)
        );
    }

    @Override
    public void puzzleTwo(String path) {
        final List<String> input = Utils.readStringList(path);
        final long time = Long.parseLong(input.get(0).substring(11).replace(" ", ""));
        final long distance = Long.parseLong(input.get(1).substring(11).replace(" ", ""));
        System.out.println(
                LongStream.range(1, time).filter(press -> (press * (time - press)) > distance).count()
        );
    }
}