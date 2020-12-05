/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day5;

import solutions.AbstractSolution;

public class Solution1  extends AbstractSolution {

    public Solution1() {
        this(1);
    }

    protected Solution1(final int solutionNumber) {
        super(solutionNumber, 5);
    }

    @Override
    public void run() {
        super.run();

        long result = this.getInputStream()
                .parallel()
                .map(this::getSeatId)
                .max(Long::compareTo)
                .get();

        this.printResult(result);

    }

    protected long getSeatId(final String seatCode) {

        final long rowIndex = binarySpacePartition(seatCode.substring(0,7), 0, 127, 'B', 'F');
        final long columnIndex = binarySpacePartition(seatCode.substring(7), 0, 7, 'R', 'L');

        return rowIndex * 8 + columnIndex;
    }

    private long binarySpacePartition(final String instructions, final int lowStart, final int highStart, final char highChar, final char lowChar) {
        double low = lowStart;
        double high = highStart;

        for (char c: instructions.toCharArray()) {

            double temp = (high + low) / 2D;
            if ( c == lowChar) {
                high = Math.floor(temp);
            } else if (c == highChar) {
                low = Math.ceil(temp);
            }
        }

        return instructions.charAt(instructions.length() - 1) == highChar ? (long)high : (long)low;
    }

}
