/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day6;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import solutions.AbstractSolution;

public class Solution1 extends AbstractSolution {

    static final String INPUT_FILENAME = "Input6";

    public Solution1() {
        this(1);
    }

    Solution1(int solutionNumber) {
        super(solutionNumber, 6);
    }

    @Override
    public void run() {
        super.run();

        List<String> orbits = this.getInput(INPUT_FILENAME, Solution1.class);

        this.printResult(countOrbits(buildOrbitMap(orbits)));

    }

    private int countOrbits(Map<String, Orbit> orbitMap) {
        int count = 0;

        for (Orbit orbit : orbitMap.values()) {
            Orbit o = orbit;
            while (o.parent != null) {
                o = o.parent;
                count++;
            }
        }

        return count;
    }

    Map<String, Orbit> buildOrbitMap(List<String> mapEntries) {
        Map<String, Orbit> orbitMap = new HashMap<>();

        for (String entry : mapEntries) {
            String[] vals = entry.split("\\)");

            Orbit parent = orbitMap.getOrDefault(vals[0], new Orbit(vals[0]));
            Orbit child = orbitMap.getOrDefault(vals[1], new Orbit(vals[1]));

            child.setParent(parent);

            orbitMap.putIfAbsent(vals[0], parent);
            orbitMap.putIfAbsent(vals[1], child);
        }

        return orbitMap;
    }

    class Orbit {
        Orbit parent;
        String identifier;

        Orbit(String identifier) {
            this.identifier = identifier;
        }

        void setParent(final Orbit other) {
            this.parent = other;
        }
    }
}
