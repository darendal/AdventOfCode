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
        System.out.printf("\n\n====== Running Solution #%s for day %s ======\n\n%n", solutionNumber, dayNumber);
    }

    protected List<String> getInput(final String filename) throws IllegalArgumentException {
        InputStream i = ClassLoader.getSystemResourceAsStream(filename);

        if (i == null) {
            throw new IllegalArgumentException("file not found");
        }

        BufferedReader r = new BufferedReader(new InputStreamReader(i));

        return r.lines().collect(Collectors.toList());
    }

    protected  List<String> getInput() throws IllegalArgumentException {
        return this.getInput("input" + this.dayNumber);
    }

    @Deprecated
    protected List<String> getInput(final String filename, Class clazz) throws IllegalArgumentException {
       return getInput(filename);
    }

    protected <T> void printResult(final T result) {
        System.out.printf("Answer is %s%n", result);
    }

    protected long[] getIntCodeProgram(String filename, Class clazz) {
        String input = this.getInput(filename, clazz).get(0);
        return Arrays.stream(input.split(",")).mapToLong(Long::parseLong).toArray();
    }

    protected List<List<Long>> permute(long[] arr) {
        List<List<Long>> list = new ArrayList<>();
        permuteHelper(list, new ArrayList<>(), arr);
        return list;
    }

    private void permuteHelper(List<List<Long>> list, List<Long> resultList, long[] arr) {

        // Base case
        if (resultList.size() == arr.length) {
            list.add(new ArrayList<>(resultList));
        } else {
            for (long value : arr) {

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
