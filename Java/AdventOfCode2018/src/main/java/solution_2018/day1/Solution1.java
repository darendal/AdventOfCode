/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solution_2018.day1;

import solutions.AbstractSolution;

public class Solution1 extends AbstractSolution {

    static final String INPUT_FILENAME = "Input1";

    public Solution1() {
        this(1);
    }

    Solution1(int solutionNumber) {
        super(solutionNumber, 1);
    }

    @Override
    public void run() {
        super.run();

        int result = this.getInput(INPUT_FILENAME, Solution1.class).stream().mapToInt(Integer::parseInt).sum();

        this.printResult(result);
    }
}
