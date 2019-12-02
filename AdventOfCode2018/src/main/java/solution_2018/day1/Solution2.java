/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solution_2018.day1;

import java.util.HashMap;
import java.util.Map;

public class Solution2 extends Solution1 {

    public Solution2() {
        super(2);
    }


    @Override
    public void run() {
        this.printSolutionHeader();

        int[] input = this.getInput(INPUT_FILENAME, Solution2.class).stream().mapToInt(Integer::parseInt).toArray();

        final Map<Integer, Integer> countMap = new HashMap<>();

        int frequency = 0;

        countMap.put(frequency, 1);
        while (true) {
            for (int value : input) {
                frequency += value;

                if (countMap.getOrDefault(frequency, 0) != 0) {
                    printResult(frequency);
                    return;
                } else {
                    countMap.put(frequency, 1);
                }
            }
        }
    }
}
