/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day4;

import java.util.stream.IntStream;

import solutions.AbstractSolution;

public class Solution1 extends AbstractSolution {
    private static final String INPUT_FILENAME = "Input4";

    public Solution1() {
        this(1);
    }

    Solution1(int solutionNumber) {
        super(solutionNumber, 4);
    }

    @Override
    public void run() {
        super.run();

        String input = this.getInput(INPUT_FILENAME, Solution1.class).get(0);

        String[] split = input.split("-");

        int low = Integer.parseInt(split[0]);
        int hi = Integer.parseInt(split[1]);

        final long result = IntStream.range(low, hi).filter(this::testInt).count();

        this.printResult(result);

    }

    private boolean testInt(int value) {

        String val = value + "";
        char prev = '0';
        int[] count = new int[9];

        for (char c : val.toCharArray()) {
            if (prev > c) {
                return false;
            }

            count[c - '1']++;
            prev = c;
        }

        return checkCount(count);
    }

    boolean checkCount(int[] count) {
        for (int i : count) {
            if (i >= 2) {
                return true;
            }
        }

        return false;
    }
}
