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

        final long rowIndex = binarySpacePartition(seatCode.substring(0,7), 'B', 'F');
        final long columnIndex = binarySpacePartition(seatCode.substring(7), 'R', 'L');

        return rowIndex * 8 + columnIndex;
    }

    private long binarySpacePartition(final String instructions, final char highChar, final char lowChar) {
        return Integer.parseInt(instructions.replace(lowChar, '0').replace(highChar, '1'), 2);
    }

}
