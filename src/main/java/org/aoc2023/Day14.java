package org.aoc2023;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Day14 implements IDay {

    public static void main(String[] args) {
        new Day14().runTwo();
    }

    @Override
    public void puzzleOne(String path) {
        List<String> items = Arrays.stream(Utils.readString(path).split(",")).toList();
        System.out.println(items.stream().map(Day14::calculateValue).reduce(Integer::sum).orElse(0));
    }

    @Override
    public void puzzleTwo(String path) {
        List<String> items = Arrays.stream(Utils.readString(path).split(",")).toList();
        Map<Integer, List<String>> map = new HashMap<>();
        IntStream.rangeClosed(0, 255).forEach(n -> map.put(n, new ArrayList<>()));
        items.forEach(command -> {
            String lens = command.split("(=)|(-)")[0];
            int value = calculateValue(lens);
            if (command.contains("=")) {
                String val = command.replaceAll("(=)|(-)", " ");
                Optional<String> replace = map.get(value).stream().filter(s -> s.startsWith(lens)).findFirst();
                if (replace.isPresent()) {
                    map.get(value).set(map.get(value).indexOf(replace.get()), val);
                } else {
                    map.get(value).add(val);
                }
            } else {
                map.get(value).removeIf(s -> s.startsWith(lens));
            }
        });

        System.out.println(map.entrySet().stream().map(entry ->
                IntStream.rangeClosed(1, entry.getValue().size())
                        .map(slot -> (entry.getKey() + 1) * slot * Integer.parseInt(entry.getValue().get(slot - 1).split(" ")[1]))
                        .reduce(Integer::sum).orElse(0)
        ).reduce(Integer::sum));
    }

    private static int calculateValue(String command) {
        AtomicInteger value = new AtomicInteger();
        command.chars().forEach(ch -> {
            value.addAndGet(ch);
            value.set(value.get() * 17);
            value.set(value.get() % 256);
        });
        return value.get();
    }

}
