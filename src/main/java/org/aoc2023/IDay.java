package org.aoc2023;

interface IDay {

    default String getPath() {
        return this.getClass().getSimpleName().toLowerCase() + ".txt";
    }

    default void run() {
        initData(getPath());
        puzzleOne(getPath());
        puzzleTwo(getPath());
    }

    default void test() {
        initData("test_" + getPath());
        puzzleOne("test_" + getPath());
        puzzleTwo("test_" + getPath());
    }

    default void initData(String path) {
    }

    void puzzleOne(String path);

    void puzzleTwo(String path);
}