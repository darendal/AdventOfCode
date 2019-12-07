/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day5;

import solutions.AbstractSolution;
import solutions.helpers.IntCode;

public class Solution1 extends AbstractSolution {

    static final String INPUT_FILENAME = "Input5";
    private static final int[] INPUT = {1};

    public Solution1() {
        this(1);
    }

    Solution1(int solutionNumber) {
        super(solutionNumber, 5);
    }

    @Override
    public void run() {
        super.run();

        int[] program = this.getIntCodeProgram(INPUT_FILENAME, Solution1.class);

        final IntCode ic = new IntCode(program, INPUT);

        ic.processIntcodeProgram();

        int result = ic.getOutput().pop();

        this.printResult(result);

    }
}
