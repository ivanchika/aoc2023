package org.aoc2023;

import java.util.*;
import java.util.stream.IntStream;

public class Day19 implements IDay {

    public static void main(String[] args) {
        new Day19().testTwo();
    }

    Map<String, List<Rule>> rulesets = new HashMap<>();

    @Override
    public void puzzleOne(String path) {
        List<String> input = Utils.readStringList(path);
        List<Condition> conditions = new ArrayList<>();
        input.forEach(s -> {
            if (!s.isBlank()) {
                if (s.startsWith("{")) conditions.add(generateConditions(s));
                else rulesets.put(s.split("\\{")[0], generateRules(s.substring(s.indexOf("{") + 1, s.indexOf("}"))));
            }
        });
        System.out.println(conditions.stream().map(this::executeFlow).peek(System.out::println)
                .reduce(Integer::sum).orElse(0));
    }

    private Condition generateConditions(String s) {
        String[] split = s.substring(1, s.length() - 1).split(",");
        Arrays.stream(split).forEach(System.out::println);
        return new Condition(Integer.parseInt(split[0].replace("x=", "")),
                Integer.parseInt(split[1].replace("m=", "")),
                Integer.parseInt(split[2].replace("a=", "")),
                Integer.parseInt(split[3].replace("s=", "")));
    }

    private List<Rule> generateRules(String rulesString) {
        String[] split = rulesString.split(",");
        String otherwise = split[split.length - 1];
        List<Rule> result = IntStream.range(0, split.length - 1).mapToObj(
                i -> Rule.of(split[i])
        ).toList();
        result.get(result.size() - 1).setOtherwise(otherwise);
        return result;
    }

    private int executeFlow(Condition condition) {
        return executeRules("in", rulesets, condition) ? condition.value : 0;
    }

    private boolean executeRules(String key, Map<String, List<Rule>> rulesets, Condition condition) {
        for (Rule rule : rulesets.get(key)) {
            int compare = switch (rule.what) {
                case 'x' -> condition.x;
                case 'm' -> condition.m;
                case 'a' -> condition.a;
                default -> condition.s;
            };
            if (rule.operation == '>') {
                if (compare > rule.value) {
                    if (rule.ifTrue.equals("A")) return true;
                    if (rule.ifTrue.equals("R")) return false;
                    return executeRules(rule.ifTrue, rulesets, condition);
                } else {
                    if (rule.otherwise != null) {
                        if (rule.otherwise.equals("A")) return true;
                        if (rule.otherwise.equals("R")) return false;
                        return executeRules(rule.otherwise, rulesets, condition);
                    }
                }
            } else {
                if (compare < rule.value) {
                    if (rule.ifTrue.equals("A")) return true;
                    if (rule.ifTrue.equals("R")) return false;
                    return executeRules(rule.ifTrue, rulesets, condition);
                } else {
                    if (rule.otherwise != null) {
                        if (rule.otherwise.equals("A")) return true;
                        if (rule.otherwise.equals("R")) return false;
                        return executeRules(rule.otherwise, rulesets, condition);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void puzzleTwo(String path) {
        List<String> input = Utils.readStringList(path);
        input.forEach(s -> {
            if (!s.isBlank()) {
                if (!s.startsWith("{")) rulesets.put(s.split("\\{")[0], generateRules(s.substring(s.indexOf("{") + 1, s.indexOf("}"))));
            }
        });

        long res = 0;
        for (int x = 1; x <= 4000; x++) {
            for (int m = 1; m <= 4000; m++) {
                for (int a = 1; a <= 4000; a++) {
                    for (int s = 1; s <= 4000; s++) {
                        if (executeRules("in", rulesets, new Condition(x, m, a, s))) res++;
                    }
                }
            }
        }
        System.out.println(res);
    }

    static class Rule {
        char what;
        char operation;
        Integer value;
        String ifTrue;
        String otherwise;

        private Rule(char what, char operation, Integer value, String ifTrue) {
            this.what = what;
            this.operation = operation;
            this.value = value;
            this.ifTrue = ifTrue;
        }

        public static Rule of(String line) {
            String left = line.split(":")[0];
            String right = line.split(":")[1];
            String[] whatIf = right.split(",");
            return new Rule(left.charAt(0), left.charAt(1), Integer.parseInt(left.substring(2)), whatIf[0]);
        }

        public void setOtherwise(String otherwise) {
            this.otherwise = otherwise;
        }

        @Override
        public String toString() {
            return "Rule{" +
                    "what='" + what + '\'' +
                    ", operation='" + operation + '\'' +
                    ", value=" + value +
                    ", ifTrue='" + ifTrue + '\'' +
                    ", otherwise='" + otherwise + '\'' +
                    '}';
        }
    }

    static class Condition {
        int x;
        int m;
        int a;
        int s;

        int value;

        public Condition(int x, int m, int a, int s) {
            this.x = x;
            this.m = m;
            this.a = a;
            this.s = s;
            this.value = x + m + a + s;
        }

        @Override
        public String toString() {
            return "Condition{" +
                    "x=" + x +
                    ", m=" + m +
                    ", a=" + a +
                    ", s=" + s +
                    ", value=" + value +
                    '}';
        }
    }
}