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

        final int[] memory = this.getIntCodeProgram(INPUT_FILENAME, Solution1.class);

        final List<List<Integer>> phaseSettings = this.permute(new int[]{0, 1, 2, 3, 4});

        int maxAmp = 0;

        for (List<Integer> phases : phaseSettings) {

            int input = 0;
            IntCode ic;
            for (int phase : phases) {
                ic = new IntCode(memory.clone(), new int[]{input, phase});

                ic.processIntcodeProgram();
                input = ic.getOutput().pop();
            }

            maxAmp = Math.max(maxAmp, input);
        }

        this.printResult(maxAmp);
    }
}
