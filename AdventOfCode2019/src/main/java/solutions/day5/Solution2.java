/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day5;

import solutions.helpers.IntCode;

public class Solution2 extends Solution1 {
    private final long[] INPUT = {5};

    public Solution2() {
        super(2);
    }

    @Override
    public void run() {
        this.printSolutionHeader();
        long[] program = this.getIntCodeProgram(INPUT_FILENAME, Solution1.class);

        final IntCode ic = new IntCode(program, INPUT);

        ic.processIntcodeProgram();

        long result = ic.getOutput().pop();

        this.printResult(result);

    }
}
