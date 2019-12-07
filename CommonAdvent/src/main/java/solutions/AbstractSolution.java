/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AbstractSolution {

    private final int solutionNumber;
    private final int dayNumber;

    protected AbstractSolution(final int solutionNumber, final int dayNumber) {
        this.solutionNumber = solutionNumber;
        this.dayNumber = dayNumber;
    }

    public void run() {
        this.printSolutionHeader();
    }

    public int getDayNumber() {
        return this.dayNumber;
    }

    protected void printSolutionHeader() {
        System.out.println(String.format("\n\n====== Running Solution #%s for day %s ======\n\n", solutionNumber, dayNumber));
    }

    protected List<String> getInput(final String filename, Class clazz) throws IllegalArgumentException {

        InputStream i = clazz.getClassLoader().getSystemResourceAsStream(filename);

        if (i == null) {
            throw new IllegalArgumentException("file not found");
        }

        BufferedReader r = new BufferedReader(new InputStreamReader(i));

        return r.lines().collect(Collectors.toList());

    }

    protected <T> void printResult(final T result) {
        System.out.println(String.format("Answer is %s", result));
    }

    protected int[] getIntCodeProgram(String filename, Class clazz) {
        String input = this.getInput(filename, clazz).get(0);
        return Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    protected List<List<Integer>> permute(int[] arr) {
        List<List<Integer>> list = new ArrayList<>();
        permuteHelper(list, new ArrayList<>(), arr);
        return list;
    }

    private void permuteHelper(List<List<Integer>> list, List<Integer> resultList, int[] arr) {

        // Base case
        if (resultList.size() == arr.length) {
            list.add(new ArrayList<>(resultList));
        } else {
            for (int value : arr) {

                if (resultList.contains(value)) {
                    // If element already exists in the list then skip
                    continue;
                }
                // Choose element
                resultList.add(value);
                // Explore
                permuteHelper(list, resultList, arr);
                // Unchoose element
                resultList.remove(resultList.size() - 1);
            }
        }
    }
}
