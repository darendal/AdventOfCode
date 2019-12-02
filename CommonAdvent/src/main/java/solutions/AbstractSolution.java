/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

}
