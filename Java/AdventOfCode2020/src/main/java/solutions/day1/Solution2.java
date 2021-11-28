/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day1;

import java.util.Optional;

import org.javatuples.Pair;

import solutions.AbstractSolution;

public class Solution2 extends AbstractSolution {

    private static final String INPUT_FILENAME = "input1";

    public Solution2() {
        super(2, 1);
    }

    @Override
    public void run() {
        super.run();

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

                final Optional<Pair<Integer,Integer>> temp = findSum(potential, table);
                if(temp.isPresent()) {
                    Pair<Integer,Integer> vals = temp.get();
                    this.printResult(i * vals.getValue0() * vals.getValue1());
                    break;
                }
            }
        }

    }

    private Optional<Pair<Integer, Integer>> findSum(int value, int[] table) {

        for (int i =0; i< 2020; i++) {
            if ( i < value && table[i] == 1) {
                int potential = value - i;
                if (table[potential] == 1)
                    return Optional.of(Pair.with(i, potential));
            }
        }
        return Optional.empty();

    }
}
