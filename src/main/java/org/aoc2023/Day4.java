package org.aoc2023;

import java.util.*;

public class Day4 implements IDay {

    public static void main(String[] args) {
        new Day4().run();
    }

    @Override
    public void puzzleOne(String path) {
        List<String> cards = Utils.readStringList(path);
        System.out.println(
                cards.stream().map(card -> {
                    String[] numbers = card.split(": ")[1].split(" \\| ");
                    List<Integer> winning = Arrays.stream(numbers[0].split(" ")).filter(s -> !s.isBlank()).map(Integer::parseInt).toList();
                    List<Integer> have = new ArrayList<>(Arrays.stream(numbers[1].split(" ")).filter(s -> !s.isBlank()).map(Integer::parseInt).toList());
                    int haveCount = have.size();
                    have.removeAll(winning);
                    double winners = haveCount - have.size();
                    return winners > 0 ? (int) Math.pow(2, (winners - 1)) : 0;
                }).reduce(Integer::sum).orElse(0));
    }


    @Override
    public void puzzleTwo(String path) {
        List<String> cards = Utils.readStringList(path);
        Map<Integer, Integer> map = new HashMap<>();
        cards.forEach(card -> {
            int cardNumber = Integer.parseInt(card.split(":")[0].replace("Card", "").trim());
            String[] numbers = card.split(": ")[1].split(" \\| ");
            List<Integer> winning = Arrays.stream(numbers[0].split(" ")).filter(s -> !s.isBlank()).map(Integer::parseInt).toList();
            List<Integer> have = new ArrayList<>(Arrays.stream(numbers[1].split(" ")).filter(s -> !s.isBlank()).map(Integer::parseInt).toList());
            int haveCount = have.size();
            have.removeAll(winning);
            int winners = haveCount - have.size();
            map.put(cardNumber, map.getOrDefault(cardNumber, 0) + 1);
            if (winners > 0) {
                for (int n = cardNumber + 1; n <= cardNumber + winners; n++) {
                    map.put(n, map.getOrDefault(n, 0) + map.getOrDefault(cardNumber, 0));
                }
            }
        });

        System.out.println(map.values().stream().reduce(Integer::sum).orElse(0));
    }

}
