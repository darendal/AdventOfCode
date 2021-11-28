/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day1;

import solutions.AbstractSolution;

public class Solution1 extends AbstractSolution {

    private static final String INPUT_FILENAME = "Input1";

    public Solution1() {
        this(1);
    }

    Solution1(int solutionNumber) {
        super(solutionNumber, 1);
    }

    @Override
    public void run() {
        super.run();

        long result = this.getInput(INPUT_FILENAME, this.getClass()).stream().mapToLong(Long::parseLong).map(this::calculateFuelForModule).sum();

        this.printResult(result);

    }

    long calculateFuelForModule(long mass) {
        return (mass / 3) - 2;
    }
}
