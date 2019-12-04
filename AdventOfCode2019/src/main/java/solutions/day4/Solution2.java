/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day4;

public class Solution2 extends Solution1 {

    public Solution2() {
        super(2);
    }

    @Override
    boolean checkCount(int[] count) {
        for (int i : count) {
            if (i == 2) {
                return true;
            }
        }

        return false;
    }
}
