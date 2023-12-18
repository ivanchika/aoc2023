package org.aoc2023;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Day18 implements IDay {

    public static void main(String[] args) {
        new Day18().testTwo();
    }

    @Override
    public void puzzleOne(String path) {
        List<String> input = Utils.readStringList(path);
        Polygon polygon = new Polygon();
        AtomicInteger x = new AtomicInteger();
        AtomicInteger y = new AtomicInteger();

        input.forEach(line -> {
            String[] split = line.split(" ");
            char direction = split[0].charAt(0);
            int steps = Integer.parseInt(split[1]);
            if (direction == 'U') {
                y.addAndGet(steps);
            } else if (direction == 'L') {
                x.addAndGet(-steps);
            } else if (direction == 'D') {
                y.addAndGet(-steps);
            } else if (direction == 'R') {
                x.addAndGet(steps);
            }
            polygon.addPoint(x.get(), y.get());
        });
        Polygon mirrored = new Polygon(Arrays.stream(polygon.xpoints).map(xx -> -xx).toArray(), Arrays.stream(polygon.ypoints).map(yy -> -yy).toArray(), polygon.npoints);
        System.out.println(
                IntStream.rangeClosed((int) polygon.getBounds2D().getMinY(), (int) polygon.getBounds2D().getMaxY()).flatMap(
                        yy -> IntStream.rangeClosed((int) polygon.getBounds2D().getMinX(), (int) polygon.getBounds2D().getMaxX()).filter(xx -> contains(polygon, mirrored, xx, yy))
                ).count());

    }

    @Override
    public void puzzleTwo(String path) {
        List<String> input = Utils.readStringList(path);
        Polygon polygon = new Polygon();
        AtomicInteger x = new AtomicInteger(0);
        AtomicInteger y = new AtomicInteger(0);
        input.forEach(line -> {
            String instr = line.substring(line.indexOf("(") + 2).replace(")", "");
            char direction = instr.charAt(instr.length() - 1);
            int steps = Integer.parseInt(instr.substring(0, instr.length() - 1), 16);
            if (direction == '3') {
                y.addAndGet(steps);
            } else if (direction == '2') {
                x.addAndGet(-steps);
            } else if (direction == '1') {
                y.addAndGet(-steps);
            } else if (direction == '0') {
                x.addAndGet(steps);
            }
            polygon.addPoint(x.get(), y.get());
        });

        Polygon mirrored = new Polygon(Arrays.stream(polygon.xpoints).map(xx -> -xx).toArray(), Arrays.stream(polygon.ypoints).map(yy -> -yy).toArray(), polygon.npoints);
        System.out.println(
                IntStream.rangeClosed((int) polygon.getBounds2D().getMinY(), (int) polygon.getBounds2D().getMaxY()).flatMap(
                        yy -> IntStream.rangeClosed((int) polygon.getBounds2D().getMinX(), (int) polygon.getBounds2D().getMaxX()).filter(xx -> contains(polygon, mirrored, xx, yy))
                ).count());
    }

    static boolean onVertex(Polygon p, int x, int y) {
        return IntStream.range(0, p.npoints).anyMatch(i -> p.xpoints[i] == x && p.ypoints[i] == y);
    }

    static boolean contains(Polygon p, Polygon m, int x, int y) {
        return p.contains(x, y) || onVertex(p, x, y) || m.contains(-x, -y);
    }


}