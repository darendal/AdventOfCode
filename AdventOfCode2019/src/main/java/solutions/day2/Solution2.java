/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day2;

import solutions.helpers.IntCode;

public class Solution2 extends Solution1 {
    private static final int EXPECTED_OUTPUT = 19690720;

    public Solution2() {
        super(2);
    }

    @Override
    public void run() {
        this.printSolutionHeader();

        final long[] memory = this.getProgram();

        for (int noun = 0; noun <= 99; noun++) {
            for (int verb = 0; verb <= 99; verb++) {
                long[] program = memory.clone();

                program = new IntCode(program).processIntcodeProgram(noun, verb);

                if (program[0] == EXPECTED_OUTPUT) {
                    this.printResult((100 * noun) + verb);
                    return;
                }
            }
        }

    }
}
