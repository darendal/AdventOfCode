/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day1;


import solutions.AbstractSolution;

public class Solution1 extends AbstractSolution {

    private static final String INPUT_FILENAME = "input1";

    public Solution1() {
        super(1, 1);
    }

    @Override
    public void run() {
        super.run();

        this.printResult(getResult());
    }

    private int getResult() {
        final int[] table = new int[2020];

        this.getInput(INPUT_FILENAME, this.getClass()).stream()
                .mapToInt(Integer::parseInt)
                .forEach(value -> {
                    if (value < 2020 && value > 0) {
                        table[value] = 1;
                    }
                });

        for (int i = 0; i< 2020; i++) {
            if(table[i] == 1) {
                final int potential = 2020 - i;

                if(table[potential] == 1) {
                    return i * potential;
                }
            }
        }

        return 0;
    }
}
