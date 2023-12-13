package org.aoc2023;

interface IDay {

    default String getPath() {
        return this.getClass().getSimpleName().toLowerCase() + ".txt";
    }

    default void run() {
        runOne();
        runTwo();
    }

    default void runOne() {
        initData(getPath());
        puzzleOne(getPath());
    }

    default void runTwo() {
        initData(getPath());
        puzzleTwo(getPath());
    }

    default void test() {
        testOne();
        testTwo();
    }

    default void testOne() {
        initData("test_" + getPath());
        puzzleOne("test_" + getPath());
    }

    default void testTwo() {
        initData("test_" + getPath());
        puzzleTwo("test_" + getPath());
    }

    default void initData(String path) {
    }

    void puzzleOne(String path);

    void puzzleTwo(String path);
}