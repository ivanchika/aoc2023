package org.aoc2023;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day7 implements IDay {

    static String ORDER;

    public static void main(String[] args) {
        new Day7().run();
    }

    @Override
    public void puzzleOne(String path) {
        ORDER = "23456789TJQKA";
        final List<Hand> sorted = Utils.readStringList(path).stream()
                .map(s -> new Hand(s, false))
                .sorted(new HandsComparator())
                .toList();
        System.out.println(IntStream.rangeClosed(1, sorted.size())
                .mapToLong(i -> (long) sorted.get(i - 1).bid * i)
                .sum());
    }

    static class HandsComparator implements Comparator<Hand> {
        @Override

        public int compare(Hand handOne, Hand handTwo) {
            if (handOne.score > handTwo.score) return 1;
            if (handOne.score < handTwo.score) return -1;
            if (handOne.cards.charAt(0) != handTwo.cards.charAt(0))
                return ORDER.indexOf(handOne.cards.charAt(0)) - ORDER.indexOf(handTwo.cards.charAt(0));
            if (handOne.cards.charAt(1) != handTwo.cards.charAt(1))
                return ORDER.indexOf(handOne.cards.charAt(1)) - ORDER.indexOf(handTwo.cards.charAt(1));
            if (handOne.cards.charAt(2) != handTwo.cards.charAt(2))
                return ORDER.indexOf(handOne.cards.charAt(2)) - ORDER.indexOf(handTwo.cards.charAt(2));
            if (handOne.cards.charAt(3) != handTwo.cards.charAt(3))
                return ORDER.indexOf(handOne.cards.charAt(3)) - ORDER.indexOf(handTwo.cards.charAt(3));
            if (handOne.cards.charAt(4) != handTwo.cards.charAt(4))
                return ORDER.indexOf(handOne.cards.charAt(4)) - ORDER.indexOf(handTwo.cards.charAt(4));
            return 0;
        }
    }

    static class Hand {
        String cards;
        Integer bid;
        Integer score;
        Integer jokers;

        public Hand(String input, boolean isJokerEnabled) {
            this.cards = input.split(" ")[0];
            this.bid = Integer.parseInt(input.split(" ")[1]);
            final Map<String, List<String>> collected = Arrays.stream(cards.split("")).collect(Collectors.groupingBy(s -> s));
            this.jokers = collected.containsKey("J") ? collected.get("J").size() : 0;
            this.score = isJokerEnabled ? getScoreWithJoker(this.cards) : getScore(this.cards);
        }

        private int getScore(String cards) {
            final Map<String, List<String>> collected = Arrays.stream(cards.split("")).collect(Collectors.groupingBy(s -> s));
            final List<Integer> groups = collected.values().stream().map(List::size).toList();

            if (groups.size() == 1) return 7; // five of a kind
            if (groups.size() == 2 && groups.containsAll(Arrays.asList(4, 1))) return 6; // four of a kind
            if (groups.size() == 2 && groups.containsAll(Arrays.asList(3, 2))) return 5; // full house
            if (groups.size() == 3 && groups.containsAll(Arrays.asList(3, 1, 1))) return 4; // three of find
            if (groups.size() == 3 && groups.containsAll(Arrays.asList(2, 2, 1))) return 3; // two pair
            if (groups.size() == 4) return 2; // one pair
            if (groups.size() == 5) return 1; // high card
            return 0;
        }

        private int getScoreWithJoker(String cards) {
            final Map<String, List<String>> collected = Arrays.stream(cards.split("")).collect(Collectors.groupingBy(s -> s));
            final List<Integer> groupsList = collected.entrySet().stream().filter(entry -> !entry.getKey().equals("J")).map(en ->
                    en.getValue().size()).toList();
            final Integer max = groupsList.stream().max(Integer::compareTo).orElse(0);
            if (max + jokers >= 5) return 7; // five of a kind
            if (max + jokers >= 4) return 6; // four of a kind
            if (groupsList.containsAll(Arrays.asList(3, 2))
                    || (jokers == 1 && (groupsList.size() == 2 && groupsList.containsAll(Arrays.asList(2, 2)) || groupsList.containsAll(Arrays.asList(3, 1))))
                    || (jokers == 2 && (groupsList.containsAll(Arrays.asList(1, 2)) || max == 3)))
                return 5; // full house
            if (groupsList.containsAll(Arrays.asList(3, 1, 1))
                    || (jokers == 1 && (groupsList.containsAll(Arrays.asList(2, 1, 1)) || groupsList.containsAll(Arrays.asList(3, 1))))
                    || (jokers == 2 && (groupsList.size() == 3))) return 4; // three of find
            if (groupsList.size() == 3 && groupsList.containsAll(Arrays.asList(2, 2, 1))
                    || (jokers == 1 && (groupsList.containsAll(Arrays.asList(2, 1)))
                    || (jokers == 2))) return 3; // two pair
            if (max + jokers == 2) return 2; // one pair
            if (max + jokers == 1) return 1; // high card
            return 0;
        }

        @Override
        public String toString() {
            return "Hand{" +
                    "cards='" + cards + '\'' +
                    ", bid=" + bid +
                    ", score=" + score +
                    ", jokers=" + jokers +
                    '｝';
        }
    }

    @Override
    public void puzzleTwo(String path) {
        ORDER = "J23456789TQKA";
        final List<Hand> sorted = Utils.readStringList(path).stream()
                .map(s -> new Hand(s, true))
                .sorted(new HandsComparator())
                .toList();
        System.out.println(IntStream.rangeClosed(1, sorted.size())
                .mapToLong(i -> (long) sorted.get(i - 1).bid * i)
                .sum());
    }


}
