/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day9;

import solutions.AbstractSolution;
import solutions.helpers.IntCode;

public class Solution1 extends AbstractSolution {

    private static final String INPUT_FILENAME = "Input9";
    private final long input;

    public Solution1() {
        this(1, 1);

    }

    Solution1(int solutionNumber, int input) {
        super(solutionNumber, 9);
        this.input = input;
    }

    @Override
    public void run() {
        super.run();

        final long[] program = this.getIntCodeProgram(INPUT_FILENAME, Solution1.class);

        final IntCode ic = new IntCode(program, new long[]{input});

        ic.processIntcodeProgram();

        this.printResult(ic.getOutput());
    }
}
