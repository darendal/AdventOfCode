/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day7;

import java.util.List;

import solutions.AbstractSolution;
import solutions.helpers.IntCode;

public class Solution1 extends AbstractSolution {

    private static final String INPUT_FILENAME = "Input7";

    public Solution1() {
        super(1, 7);
    }

    @Override
    public void run() {
        super.run();

        final long[] memory = this.getIntCodeProgram(INPUT_FILENAME, Solution1.class);

        final List<List<Long>> phaseSettings = this.permute(new long[]{0, 1, 2, 3, 4});

        long maxAmp = 0;

        for (List<Long> phases : phaseSettings) {

            long input = 0;
            IntCode ic;
            for (long phase : phases) {
                ic = new IntCode(memory.clone(), new long[]{input, phase});

                ic.processIntcodeProgram();
                input = ic.getOutput().pop();
            }

            maxAmp = Math.max(maxAmp, input);
        }

        this.printResult(maxAmp);
    }
}
