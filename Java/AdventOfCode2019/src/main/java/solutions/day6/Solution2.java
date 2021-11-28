/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day6;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Solution2 extends Solution1 {

    public Solution2() {
        super(2);
    }

    @Override
    public void run() {
        this.printSolutionHeader();

        List<String> orbits = this.getInput(INPUT_FILENAME, Solution2.class);

        Map<String, Orbit> orbitMap = this.buildOrbitMap(orbits);

        Orbit myOrbit = orbitMap.get("YOU");
        Orbit santaOrbit = orbitMap.get("SAN");

        Orbit closestOrbit = getClosestCommonOrbit(myOrbit.parent, santaOrbit.parent, orbitMap);

        this.printResult(countOrbits(myOrbit.parent, closestOrbit) + countOrbits(santaOrbit.parent, closestOrbit));
    }

    private Orbit getClosestCommonOrbit(Orbit o1, Orbit o2, Map<String, Orbit> orbitMap) {
        Set<String> o1Orbits = buildOrbitSet(o1);
        Set<String> o2Orbits = buildOrbitSet(o2);

        o2Orbits.retainAll(o1Orbits);

        Optional<OrbitDepthTuple> odt = o2Orbits.stream()
                .map(i -> new OrbitDepthTuple(i, countOrbits(orbitMap.get(i), orbitMap.get("COM"))))
                .max(Comparator.comparing(c -> c.depth));

        return orbitMap.get(odt.get().orbit);

    }

    private int countOrbits(Orbit source, Orbit dest) {

        int count = 0;

        Orbit o = source;

        while (o.parent != null) {
            count++;

            if (o.parent.identifier.equals(dest.identifier)) {
                return count;
            }

            o = o.parent;
        }

        return -1;
    }

    private Set<String> buildOrbitSet(Orbit orbit) {
        Set<String> myOrbits = new HashSet<>();

        Orbit o = orbit;

        while (o.parent != null) {
            myOrbits.add(o.identifier);
            o = o.parent;
        }

        return myOrbits;
    }

    private class OrbitDepthTuple {
        String orbit;
        int depth;

        OrbitDepthTuple(String o, int depth) {
            this.orbit = o;
            this.depth = depth;
        }
    }
}
