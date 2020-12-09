/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day9;


import java.util.HashMap;
import java.util.Map;

import solutions.AbstractSolution;

public class Solution1 extends AbstractSolution {

    private static final int preambleLength = 25;

    public Solution1() {
        super(1, 9);
    }

    @Override
    public void run() {
        super.run();

        final long[] input = this.getInputStream().mapToLong(Long::parseLong).toArray();

        for (int i = preambleLength; i < input.length; i++) {
            if (!this.twoSum(input, i - preambleLength, i,  input[i])) {
                this.printResult(input[i]);
                break;
            }
        }

    }

    private boolean twoSum(long[] input, int beginning, int end, long sum) {

        Map<Long, Boolean> map = new HashMap<>();

        for (int i = beginning; i < end ; i++) {
            long compliment = sum - input[i];

            if (map.getOrDefault(compliment, false)) {
                return true;
            } else {
                map.put(input[i], true);
            }
        }

        return false;
    }
}
