/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day8;

public class Solution2 extends Solution1 {

    public Solution2() {
        super(2);
    }

    @Override
    public void run() {
        this.printSolutionHeader();

        Layer finalLayer = buildLayers().stream().reduce(Layer::add).get();

        this.printResult("");
        finalLayer.print();
    }
}
