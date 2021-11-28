/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package utils;

public class CommonUtils {

    public static int manhattanDistance(int start_x, int start_y, int end_x, int end_y) {
        // |x1 - x2| + |y1 - y2|
        return Math.abs(start_x - end_x) + Math.abs(start_y - end_y);
    }
}
