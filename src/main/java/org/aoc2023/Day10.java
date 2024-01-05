package org.aoc2023;

import java.util.stream.Stream;

public class Day10 implements IDay {

    public static void main(String[] args) {
        new Day10().runOne();
    }

    @Override
    public void puzzleOne(String path) {
        final char[][] chars = Utils.readChar2DArrayList(path);
        int sX = -1;
        int sY = -1;
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

        System.out.println(Stream.of(
                        (calculate(chars, sX - 1, sY, 'L') + 1) / 2,
                        (calculate(chars, sX + 1, sY, 'R') + 1) / 2,
                        (calculate(chars, sX, sY - 1, 'U') + 1) / 2,
                        (calculate(chars, sX, sY + 1, 'D') + 1) / 2
                )
                .max(Integer::compareTo)
                .orElse(0));
    }

    private int calculate(char[][] chars, int sX, int sY, char d) {
        int dist = 0;
        while (sX >= 0 && sX < chars[0].length && sY >= 0 && sY < chars.length && chars[sY][sX] != 'S' && chars[sY][sX] != '.'
                && ((d == 'L' && "-FL".contains(chars[sY][sX] + ""))
                || (d == 'R' && "-J7".contains(chars[sY][sX] + ""))
                || (d == 'U' && "|F7".contains(chars[sY][sX] + ""))
                || (d == 'D' && "|JL".contains(chars[sY][sX] + "")))) {
            dist++;
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
        return dist;
    }

    @Override
    public void puzzleTwo(String path) {
    }

}
