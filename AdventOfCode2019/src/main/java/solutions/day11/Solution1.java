/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day11;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import models.ProducerConsumer;
import solutions.AbstractSolution;
import solutions.helpers.ConcurrentIntCode;

public class Solution1 extends AbstractSolution {

    private static final String INPUT_FILENAME = "Input11";

    public Solution1() {
        this(1);
    }

    Solution1(int solutionNumber) {
        super(solutionNumber, 11);
    }

    @Override
    public void run() {
        super.run();

        int result = buildRobot().countPainted();

        this.printResult(result);
    }

    HullPainter buildRobot() {
        return new HullPainter(this.getIntCodeProgram(INPUT_FILENAME, Solution1.class));
    }

    private enum Directions {

        NORTH(),
        WEST(),
        SOUTH(),
        EAST();

        static {
            NORTH.left = WEST;
            NORTH.right = EAST;

            WEST.left = SOUTH;
            WEST.right = NORTH;

            SOUTH.left = EAST;
            SOUTH.right = WEST;

            EAST.left = NORTH;
            EAST.right = SOUTH;
        }

        private Directions left;
        private Directions right;


        Directions turnLeft() {
            return this.left;
        }

        Directions turnRight() {
            return this.right;
        }


    }

    class HullPainter {
        private final ProducerConsumer<Long> programInput;
        private final ProducerConsumer<Long> programOutput;
        private final ConcurrentIntCode cpu;

        HullPainter(long[] program) {

            this.programInput = new ProducerConsumer<>();
            this.programOutput = new ProducerConsumer<>();

            this.cpu = new ConcurrentIntCode(program, programInput, programOutput);
        }

        int countPainted() {
            Set<Point> paintedPanels = new HashSet<>();

            final ExecutorService executor = Executors.newFixedThreadPool(2);

            Future<Integer> f = executor.submit(cpu);

            executor.submit(() -> {
                int x = 0;
                int y = 0;
                Directions currentDirection = Directions.NORTH;

                Map<Point, Long> colorMap = new HashMap<>();
                while (true) {

                    final Point currentPoint = new Point(x, y);

                    this.programInput.produce(colorMap.getOrDefault(currentPoint, 0L));

                    long color = this.programOutput.consume();
                    colorMap.put(currentPoint, color);

                    long direction = this.programOutput.consume();

                    paintedPanels.add(currentPoint);

                    currentDirection = direction == 0 ? currentDirection.turnLeft() : currentDirection.turnRight();

                    switch (currentDirection) {

                        case NORTH:
                            y += 1;
                            break;
                        case WEST:
                            x -= 1;
                            break;
                        case SOUTH:
                            y -= 1;
                            break;
                        case EAST:
                            x += 1;
                            break;
                    }
                }
            });

            try {
                f.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } finally {
                executor.shutdownNow();
            }
            return paintedPanels.size();
        }

        int[][] paintHull() {
            final ExecutorService executor = Executors.newFixedThreadPool(2);

            Future<Integer> f = executor.submit(cpu);

            Future<int[][]> futureHull = executor.submit(() -> {
                int[][] hull = new int[8][45];
                int x = 1;
                int y = 1;

                // Start on white
                hull[x][y] = 1;

                Directions currentDirection = Directions.NORTH;

                while (true) {

                    this.programInput.produce((long) hull[x][y]);

                    long color = this.programOutput.consume();

                    if (color == -1) {
                        return hull;
                    }

                    hull[x][y] = color == 0L ? 0 : 1;

                    long direction = this.programOutput.consume();

                    if (direction == -1) {
                        return hull;
                    }

                    currentDirection = direction == 0 ? currentDirection.turnLeft() : currentDirection.turnRight();

                    switch (currentDirection) {

                        case NORTH:
                            x -= 1;
                            break;
                        case WEST:
                            y -= 1;
                            break;
                        case SOUTH:
                            x += 1;
                            break;
                        case EAST:
                            y += 1;
                            break;
                    }
                }
            });

            int[][] result;
            try {
                if (futureHull.isDone()) {
                    throw new IllegalStateException("Hull painter stopped unexpectedly");
                }
                f.get();
                this.programOutput.produce(-1L);
                this.programOutput.produce(-1L);
                result = futureHull.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                result = new int[0][0];
            } finally {
                executor.shutdownNow();
            }

            return result;
        }


    }
}
