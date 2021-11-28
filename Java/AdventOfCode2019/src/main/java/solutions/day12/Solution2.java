/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day12;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution2 extends Solution1 {

    public Solution2() {
        super(2);
    }

    @Override
    public void run() {
        this.printSolutionHeader();

        Moon[] moons = this.buildMoons().toArray(new Moon[4]);

        long xCount = getRepeatCount(0, moons.clone());
        long yCount = getRepeatCount(1, moons.clone());
        long zCount = getRepeatCount(2, moons.clone());

        this.printResult(String.format("lcm {%s, %s, %s}", xCount, yCount, zCount));

    }

    private long getRepeatCount(int position, Moon[] moons) {

        Set<List<Point>> allPoints = new HashSet<>();

        boolean cont = true;
        long count = 0;
        while (cont) {

            List<Point> points = new ArrayList<>(4);
            List<Moon> moonList = Arrays.asList(moons);
            for (Moon m : moonList) {
                m.updateVelocity(moonList);
            }

            for (Moon m : moonList) {
                m.updatePosition();
                points.add(new Point(m.position[position], m.velocity[position]));
            }

            cont = allPoints.add(points);
            count++;
        }
        return count - 1;
    }

}
