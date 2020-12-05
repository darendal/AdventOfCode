/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day5;

public class Solution2 extends Solution1 {

    private static final int maxVal = 991;

    public Solution2() {
        super(1);
    }

    @Override
    public void run() {
        this.printSolutionHeader();

        final int[] table = new int[maxVal + 1];

        this.getInputStream()
                .map(this::getSeatId)
                .forEach(seat -> table[seat.intValue()] = 1);

        for (int i = 0; i < maxVal - 4; i++) {
            if (table[i] == 1 && table[i+1] == 0 && table[i+2] == 1) {
                this.printResult(i+1);
                break;
            }
        }
    }
}
