package org.aoc2023;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1 implements IDay {


    public static void main(String[] args) {
        new Day1().run();
    }

    @Override
    public void puzzleOne(String path) {
        List<String> strings = Utils.readStringList(path);
        System.out.println(strings.stream()
                .map(line -> line.replaceAll("[^0-9]", ""))
                .map(digits -> digits.charAt(0) + "" + digits.charAt(digits.length() - 1))
                .map(Integer::valueOf)
                .reduce(Integer::sum)
                .orElse(0));
    }

    @Override
    public void puzzleTwo(String path) {
        List<String> strings = Utils.readStringList(path);
        Map<String, String> digits = new HashMap<>();
        digits.put("one", "1");
        digits.put("two", "2");
        digits.put("three", "3");
        digits.put("four", "4");
        digits.put("five", "5");
        digits.put("six", "6");
        digits.put("seven", "7");
        digits.put("eight", "8");
        digits.put("nine", "9");


        System.out.println(strings.stream()
                .map(line -> {
                    Pattern patternStart = Pattern.compile("(\\d|one|two|three|four|five|six|seven|eight|nine)");
                    String first = "";
                    String last = "";
                    for (int i = 0; i < line.length(); i++) {
                        Matcher matcher = patternStart.matcher(line.substring(i));
                        if (matcher.find()) {
                            do {
                                String digit = matcher.group();
                                String found = digit.length() > 1 ? digits.get(digit) : digit;
                                if (first.isBlank()) {
                                    first = found;
                                }
                                last = found;
                            } while (matcher.find());
                        }
                    }
                    return first + last;
                })
                .map(Integer::valueOf)
                .reduce(Integer::sum)
                .orElse(0));
    }

}
