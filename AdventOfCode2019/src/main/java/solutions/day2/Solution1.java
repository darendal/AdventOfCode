/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day2;

import solutions.AbstractSolution;
import solutions.helpers.IntCode;

public class Solution1 extends AbstractSolution {

    private static final String INPUT_FILENAME = "Input2";

    public Solution1() {
        this(1);
    }

    Solution1(int solutionNumber) {
        super(solutionNumber, 2);
    }

    @Override
    public void run() {
        super.run();

        long[] program = getProgram();

        long[] result = new IntCode(program).processIntcodeProgram(12, 1);

        this.printResult(result[0]);

    }

    long[] getProgram() {
        return this.getIntCodeProgram(INPUT_FILENAME, Solution1.class);
    }

}
