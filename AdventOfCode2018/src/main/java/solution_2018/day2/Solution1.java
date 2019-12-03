/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solution_2018.day2;

import java.util.List;

import solutions.AbstractSolution;

public class Solution1 extends AbstractSolution {

    static final String INPUT_FILENAME = "Input2";

    public Solution1() {
        super(1, 2);
    }


    @Override
    public void run() {
        super.run();

        List<String> input = this.getInput(INPUT_FILENAME, Solution1.class);

        int twoCount = 0;
        int threeCount = 0;

        for (String s : input) {
            BoxIdStats stat = calculateIdStats(s);
            if (stat.twoCount)
                twoCount++;
            if (stat.threeCount)
                threeCount++;
        }

        this.printResult(twoCount * threeCount);

    }

    private BoxIdStats calculateIdStats(final String boxId) {

        final BoxIdStats result = new BoxIdStats();

        int[] charCount = new int[26];

        for (char c : boxId.toCharArray()) {
            charCount[c - 'a'] += 1;

        }

        for (int value : charCount) {
            if (value == 2) {
                result.twoCount = true;
            } else if (value == 3) {
                result.threeCount = true;
            }
        }

        return result;
    }

    private class BoxIdStats {

        boolean twoCount;
        boolean threeCount;

    }
}
