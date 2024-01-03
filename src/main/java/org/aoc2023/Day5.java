package org.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

public class Day5 implements IDay {
    List<SeedTo> soil = new ArrayList<>();
    List<SeedTo> fertilizer = new ArrayList<>();
    List<SeedTo> water = new ArrayList<>();
    List<SeedTo> light = new ArrayList<>();
    List<SeedTo> temperature = new ArrayList<>();
    List<SeedTo> humidity = new ArrayList<>();
    List<SeedTo> location = new ArrayList<>();
    List<Long> seeds = new ArrayList<>();

    public static void main(String[] args) {
        new Day5().run();
    }

    @Override
    public void puzzleOne(String path) {
        System.out.println(seeds.stream().map(seed ->
                getByMap(location, getByMap(humidity, getByMap(temperature, getByMap(light, getByMap(water, getByMap(fertilizer, getByMap(soil, seed)))))))
        ).min(Long::compareTo).orElse(0L));
    }

    private static long getByMap(List<SeedTo> seedTo, Long seed) {
        for (SeedTo to : seedTo) {
            if (seed >= to.from && seed <= (to.from + to.range - 1)) {
                return to.to + (seed - to.from);
            }
        }
        return seed;
    }

    private static int scanInput(int i, List<String> input, List<SeedTo> soil) {
        while (i < input.size() && !input.get(i).isBlank()) {
            if (Character.isDigit(input.get(i).charAt(0))) {
                String[] split = input.get(i).split(" ");
                soil.add(new SeedTo(Long.parseLong(split[0]),
                        Long.parseLong(split[1]),
                        Long.parseLong(split[2])));
            }
            i++;
        }
        i++;
        return i;
    }

    static class SeedTo {
        long to;
        long from;
        long range;

        public SeedTo(long to, long from, long range) {
            this.to = to;
            this.from = from;
            this.range = range;
        }

        @Override
        public String toString() {
            return "SeedTo{" +
                    "to=" + to +
                    ", from=" + from +
                    ", range=" + range +
                    '}';
        }
    }

    @Override
    public void puzzleTwo(String path) {
        List<long[]> ranges = new ArrayList<>();
        for (int idx = 0; idx < seeds.size(); idx += 2) {
            ranges.add(new long[]{seeds.get(idx), seeds.get(idx) + seeds.get(idx + 1)});
        }

        System.out.println(
                ranges.stream()
                        .flatMap(range -> LongStream.range(range[0], range[1]).boxed())
                        .parallel()
                        .mapToLong(seed -> getByMap(location, getByMap(humidity, getByMap(temperature, getByMap(light, getByMap(water, getByMap(fertilizer, getByMap(soil, seed))))))))
                        .min()
                        .orElse(0L)
        );
    }

    @Override
    public void initData(String path) {
        List<String> input = Utils.readStringList(path);
        seeds = Arrays.stream(input.get(0).replace("seeds: ", "").split(" ")).map(Long::parseLong).toList();
        int i = 2;
        i = scanInput(i, input, soil);
        i = scanInput(i, input, fertilizer);
        i = scanInput(i, input, water);
        i = scanInput(i, input, light);
        i = scanInput(i, input, temperature);
        i = scanInput(i, input, humidity);
        scanInput(i, input, location);
    }

}
