package org.aoc2023;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day10 implements IDay {

    public static void main(String[] args) {
        new Day10().run();
    }

    List<List<int[]>> loops = new ArrayList<>();
    List<int[]> maxLoop = new ArrayList<>();

    @Override
    public void puzzleOne(String path) {
        System.out.println((maxLoop.size() + 1) / 2);
    }

    private void calculate(char[][] chars, int sX, int sY, char d) {
        List<int[]> loop = new ArrayList<>();
        while (sX >= 0 && sX < chars[0].length && sY >= 0 && sY < chars.length && chars[sY][sX] != 'S' && chars[sY][sX] != '.'
                && ((d == 'L' && "-FL".contains(chars[sY][sX] + ""))
                || (d == 'R' && "-J7".contains(chars[sY][sX] + ""))
                || (d == 'U' && "|F7".contains(chars[sY][sX] + ""))
                || (d == 'D' && "|JL".contains(chars[sY][sX] + "")))) {
            loop.add(new int[]{sY, sX});
            switch (d) {
                case 'L': {
                    switch (chars[sY][sX]) {
                        case '-':
                            sX--;
                            break;
                        case 'F': {
                            sY++;
                            d = 'D';
                        }
                        break;
                        case 'L': {
                            sY--;
                            d = 'U';
                        }
                        break;
                    }

                }
                break;
                case 'R': {
                    switch (chars[sY][sX]) {
                        case '-':
                            sX++;
                            break;
                        case '7': {
                            sY++;
                            d = 'D';
                        }
                        break;
                        case 'J': {
                            sY--;
                            d = 'U';
                        }
                        break;
                    }
                }
                break;
                case 'U': {
                    switch (chars[sY][sX]) {
                        case '|':
                            sY--;
                            break;
                        case 'F': {
                            sX++;
                            d = 'R';
                        }
                        break;
                        case '7': {
                            sX--;
                            d = 'L';
                        }
                        break;
                    }
                }
                break;
                case 'D': {
                    switch (chars[sY][sX]) {
                        case '|':
                            sY++;
                            break;
                        case 'L': {
                            sX++;
                            d = 'R';
                        }
                        break;
                        case 'J': {
                            sX--;
                            d = 'L';
                        }
                        break;
                    }
                }
                break;
            }
        }
        loops.add(loop);
    }

    @Override
    public void initData(String path) {
        int sX = -1;
        int sY = -1;
        char[][] chars = Utils.readChar2DArrayList(path);
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars[0].length; j++) {
                if (chars[i][j] == 'S') {
                    sY = i;
                    sX = j;
                    break;
                }
                if (sX > -1) break;
            }
        }
        calculate(chars, sX - 1, sY, 'L');
        calculate(chars, sX + 1, sY, 'R');
        calculate(chars, sX, sY - 1, 'U');
        calculate(chars, sX, sY + 1, 'D');
        maxLoop = loops.stream().max(Comparator.comparingInt(List::size)).orElse(new ArrayList<>());
    }

    @Override
    public void puzzleTwo(String path) {
        // Pick's theorem
        // area = interior + (boundaries / 2) - 1
        int boundaries = maxLoop.size() + 1;
        double interior = area(maxLoop) - (boundaries / 2.0) + 1;
        System.out.println((int) interior);

    }

    private static double area(List<int[]> loop) {
        // Shoelace formula
        // area = (x1*y2 - x2*y1 + x2*y3 - x3*y2 + .... + xn*y1 - x1*yn) / 2
        int n = loop.size();
        double area = 0.0;
        for (int i = 0; i < n - 1; i++) {
            area += loop.get(i)[1] * loop.get(i + 1)[0] - loop.get(i + 1)[1] * loop.get(i)[0];
        }
        area += loop.get(n - 1)[1] * loop.get(0)[0] - loop.get(0)[1] * loop.get(n - 1)[0];
        return Math.abs(area) / 2.0;
    }
}
