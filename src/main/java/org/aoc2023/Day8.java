package org.aoc2023;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8 implements IDay {

    public static void main(String[] args) {
        new Day8().runOne();
    }

    @Override
    public void puzzleOne(String path) {
        List<String> input = Utils.readStringList(path);
        char[] instructions = input.get(0).toCharArray();
        Map<String, WhereToGo> directions = new HashMap<>();
        for (int i = 2; i < input.size(); i++) {
            directions.put(input.get(i).substring(0, 3), new WhereToGo(input.get(i).substring(0, 3), input.get(i).substring(7, 10), input.get(i).substring(12, 15)));
        }
        int steps = 0;
        int step = 0;
        WhereToGo next = directions.get("AAA");
        do {
            char where = instructions[step];
            if (where == 'L') next = directions.get(next.left);
            if (where == 'R') next = directions.get(next.right);
            step++;
            steps++;
            if (step == instructions.length) step = 0;
        } while (!next.name.equals("ZZZ"));

        System.out.println(steps);
    }


    static class WhereToGo {
        String name;
        String left;
        String right;

        public WhereToGo(String name, String left, String right) {
            this.name = name;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "SeedTo{" +
                    "name=" + name +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

    @Override
    public void puzzleTwo(String path) {

    }

}
