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

public class Solution1 extends AbstractSolution {

    private static final String INPUT_FILENAME = "Input3";

    public Solution1() {
        this(1);
    }

    private Solution1(int solutionNumber) {
        super(solutionNumber, 3);
    }

    @Override
    public void run() {
        super.run();

        List<Set<Point>> wires = this.getInput(INPUT_FILENAME, Solution1.class)
                .stream()
                .map(this::layoutWire)
                .collect(Collectors.toList());

        wires.get(0).retainAll(wires.get(1));

        OptionalInt result = wires.get(0).stream().mapToInt(this::manhattanDistance).min();

        this.printResult(result.orElse(0));


    }

    private Set<Point> layoutWire(String wire) {

        Set<Point> result = new HashSet<>();

        String[] wireInstructions = wire.split(",");

        Point startPoint = new Point(0, 0);

        for (String instruction : wireInstructions) {
            Point delta;
            switch (instruction.charAt(0)) {
                case 'L':
                    delta = new Point(-1, 0);
                    break;
                case 'U':
                    delta = new Point(0, 1);
                    break;
                case 'R':
                    delta = new Point(1, 0);
                    break;
                case 'D':
                    delta = new Point(0, -1);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid direction parsed");
            }

            for (int i = 0; i < Integer.parseInt(instruction.substring(1)); i++) {
                startPoint.translate(delta.x, delta.y);
                result.add(new Point(startPoint.x, startPoint.y));
            }
        }


        return result;
    }

    private int manhattanDistance(Point intersection) {
        return Math.abs(intersection.x) + Math.abs(intersection.y);
    }
}
