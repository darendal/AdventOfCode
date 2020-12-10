/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day10;

import solutions.AbstractSolution;

public class Solution1 extends AbstractSolution {

    public Solution1() {
        super(1, 10);
    }

    @Override
    public void run() {
        super.run();

        final long[] input = this.getInputStream().mapToLong(Long::parseLong).sorted().toArray();

        int oneJolt = 0;
        int threeJolt = 0;

        long currentJolts = 0;

        for (long adapter : input) {
            if (currentJolts + 1 == adapter) {
                oneJolt++;
            } else if (currentJolts + 3 == adapter) {
                threeJolt++;
            }
            currentJolts = adapter;
        }
        // Account for adapter -> device end jolt conversion.
        threeJolt++;

        System.out.printf("1 Jolt differences: %d, 3 Jolt difference: %d\n", oneJolt, threeJolt);
        this.printResult(oneJolt * threeJolt);

    }
}
