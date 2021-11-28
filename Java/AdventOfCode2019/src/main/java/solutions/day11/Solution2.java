/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day11;

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

        int[][] result = this.buildRobot().paintHull();

        this.printResult("");

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                System.out.print(String.format(TEMPLATE,
                        result[i][j] == 0 ? ANSI_BLACK_BACKGROUND : ANSI_WHITE_BACKGROUND,
                        ANSI_RESET));
            }
            System.out.println();
        }
    }
}
