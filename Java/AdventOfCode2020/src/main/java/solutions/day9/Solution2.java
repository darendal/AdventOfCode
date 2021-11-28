/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day9;

import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import solutions.AbstractSolution;

public class Solution2 extends AbstractSolution {

    // Solution to part #1
    private static final long target = 556543474;

    public Solution2() {
        super(2, 9);
    }

    @Override
    public void run() {
        super.run();

        final List<Long> input = this.getInputStream().map(Long::parseLong).collect(Collectors.toList());

        int min = 0;
        int max = 1;
        long sum = input.get(min) + input.get(max);

        while (true) {
            if (sum != target) {
                if (sum < target) {
                    max++;
                    sum += input.get(max);
                } else {
                    sum -= input.get(min);
                    min++;
                }
            } else {
                LongSummaryStatistics stats = input.subList(min, max).stream().mapToLong(x->x).summaryStatistics();

                long minSub = stats.getMin();
                long maxSub = stats.getMax();

                this.printResult(minSub + maxSub);

                break;
            }
        }
    }
}
