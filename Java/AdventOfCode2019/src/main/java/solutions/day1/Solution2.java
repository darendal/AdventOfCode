/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day1;

public class Solution2 extends Solution1 {

    public Solution2() {
        super(2);
    }

    @Override
    long calculateFuelForModule(long mass) {
        long newMass = (mass / 3) - 2;

        return newMass > 0 ? newMass + calculateFuelForModule(newMass) : 0;

    }
}
