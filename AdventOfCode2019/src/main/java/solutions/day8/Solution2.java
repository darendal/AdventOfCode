/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day8;

import java.util.List;

public class Solution2 extends Solution1 {

    private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    private static final String ANSI_RESET = "\u001B[0m";

    private static final String TEMPLATE = "%s %s";

    public Solution2() {
        super(2);
    }

    @Override
    public void run() {
        this.printSolutionHeader();

        List<Layer> layers = buildLayers();

        Layer finalLayer = layers.stream().reduce(Layer::add).get();

        for (int i = 0; i < finalLayer.height; i++) {
            for (int j = 0; j < finalLayer.width; j++) {
                System.out.print(String.format(TEMPLATE,
                        finalLayer.layer[i][j] == 0 ? ANSI_BLACK_BACKGROUND : ANSI_WHITE_BACKGROUND,
                        ANSI_RESET));
            }
            System.out.println();
        }

    }
}
