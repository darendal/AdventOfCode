/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day3;

import java.util.List;
import java.util.stream.Stream;

public class Solution2 extends Solution1 {

    public Solution2() {
        super(2);
    }

    @Override
    public void run() {
        this.printSolutionHeader();

        final List<String> map = this.getInput();

        long result = Stream.of(
                new Slope(1, 1),
                new Slope(1, 3),
                new Slope(1, 5),
                new Slope(1, 7),
                new Slope(2, 1))
                .map(slope -> (long)this.evaluateSlope(map, slope))
                .reduce(1L,(subtotal, elem) -> subtotal * elem);

        this.printResult(result);
    }
}
