/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day3;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;

import solutions.AbstractSolution;

public class Solution2 extends AbstractSolution {

    private static final String INPUT_FILENAME = "Input3";

    public Solution2() {
        super(2, 3);
    }

    @Override
    public void run() {
        super.run();

        List<Set<TimePoint>> wires = this.getInput(INPUT_FILENAME, Solution1.class)
                .stream()
                .map(this::layoutWire)
                .collect(Collectors.toList());

        int result = Integer.MAX_VALUE;


        wires.get(0).retainAll(wires.get(1));
        wires.get(1).retainAll(wires.get(0));

        for (TimePoint p : wires.get(0)) {
            OptionalInt i = wires.get(1).stream()
                    .filter(o -> o.sameLocation(p))
                    .mapToInt(o -> o.time + p.time)
                    .min();

            result = Math.min(result, i.orElse(Integer.MAX_VALUE));
        }

        this.printResult(result);

    }

    private Set<TimePoint> layoutWire(String wire) {

        Set<TimePoint> result = new HashSet<>();

        String[] wireInstructions = wire.split(",");

        TimePoint startPoint = new TimePoint(0, 0, 0);

        for (String instruction : wireInstructions) {
            TimePoint delta;
            switch (instruction.charAt(0)) {
                case 'L':
                    delta = new TimePoint(-1, 0, 1);
                    break;
                case 'U':
                    delta = new TimePoint(0, 1, 1);
                    break;
                case 'R':
                    delta = new TimePoint(1, 0, 1);
                    break;
                case 'D':
                    delta = new TimePoint(0, -1, 1);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid direction parsed");
            }

            for (int i = 0; i < Integer.parseInt(instruction.substring(1)); i++) {
                startPoint.translate(delta);
                result.add(new TimePoint(startPoint.point.x, startPoint.point.y, startPoint.time));
            }
        }


        return result;
    }

    private class TimePoint {
        private Point point;
        private int time;

        TimePoint(int x, int y, int time) {
            this.point = new Point(x, y);
            this.time = time;
        }

        @Override
        public int hashCode() {
            return this.point.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof TimePoint) {
                TimePoint pt = (TimePoint) obj;
                return point.equals(pt.point);
            }
            return super.equals(obj);
        }

        void translate(TimePoint newLocation) {
            this.point.translate(newLocation.point.x, newLocation.point.y);
            this.time += newLocation.time;
        }

        boolean sameLocation(TimePoint other) {
            return this.point.equals(other.point);
        }

    }
}
