package org.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day9 implements IDay {

    List<List<Integer>> list;

    public static void main(String[] args) {
        new Day9().run();
    }

    @Override
    public void initData(String path) {
        final List<String> input = Utils.readStringList(path);
        list = input.stream()
                .map(line -> Arrays.stream(line.split(" "))
                        .map(Integer::valueOf)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    @Override
    public void puzzleOne(String path) {
        System.out.println(list.stream().map(this::calculateOne).reduce(0, Integer::sum));
    }


    private Integer calculateOne(List<Integer> oasis) {
        int result = oasis.get(oasis.size() - 1);
        List<Integer> diffs;
        do {
            diffs = new ArrayList<>();
            for (int i = 1; i < oasis.size(); i++) {
                diffs.add(oasis.get(i) - oasis.get(i - 1));
            }
            result += diffs.get(diffs.size() - 1);
            oasis = diffs;
        } while (diffs.stream().anyMatch(v -> v != 0));
        return result;
    }

    private Integer calculateTwo(List<Integer> oasis) {
        int result = 0;
        List<Integer> diffs = new ArrayList<>();
        diffs.add(oasis.get(0));
        List<Integer> diff;
        do {
            diff = new ArrayList<>();
            for (int i = 1; i < oasis.size(); i++) {
                diff.add(oasis.get(i) - oasis.get(i - 1));
            }
            oasis = diff;
            diffs.add(diff.get(0));
        } while (diff.stream().anyMatch(v -> v != 0));
        Collections.reverse(diffs);
        for (Integer val : diffs) {
            result = val - result;
        }
        return result;
    }

    @Override
    public void puzzleTwo(String path) {
        System.out.println(list.stream().map(this::calculateTwo).reduce(0, Integer::sum));
    }


}