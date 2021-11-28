/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day10;

import java.util.Comparator;
import java.util.List;

public class Solution2 extends Solution1 {

    public Solution2() {
        super(2);
    }

    @Override
    public void run() {
        this.printSolutionHeader();

        List<Asteroid> asteroids = this.buildAsteroidList();

        asteroids.forEach(origin -> origin.populateMap(asteroids));

        Asteroid station = asteroids.stream().max(Comparator.comparing(Asteroid::visibleAsteroids)).get();


        Asteroid last = (Asteroid) station.destroyVisibleAsteroid().toArray()[199];

        this.printResult((last.x * 100) + last.y);

    }
}
