/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day3;

import java.util.List;

import solutions.AbstractSolution;

public class Solution1 extends AbstractSolution {

    public Solution1() {
        this(1);
    }

    protected Solution1(int solutionNumber) {
        super(solutionNumber, 3);
    }

    @Override
    public void run() {
        super.run();

        this.printResult(evaluateSlope(this.getInput(), new Slope(1, 3)));
    }

    protected int evaluateSlope(final List<String> map, final Slope slope) {

        final int length = map.get(0).length();

        int x = 0;
        int y = 0;
        int trees = 0;

        while (y < map.size() - slope.rise) {
            x += slope.run;
            y += slope.rise;

            if ( map.get(y).charAt(x % length) == '#')
                trees++;
        }
        return trees;
    }

    protected static class Slope {
        public final int rise;
        public final int run;

        Slope(final int rise, final int run) {
            this.rise = rise;
            this.run = run;
        }
    }
}
