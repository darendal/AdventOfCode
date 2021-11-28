/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day12;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import solutions.AbstractSolution;

public class Solution1 extends AbstractSolution {

    private static final String INPUT_FILENAME = "Input12";

    public Solution1() {
        this(1);
    }

    Solution1(int solutionNumber) {
        super(solutionNumber, 12);
    }

    @Override
    public void run() {
        super.run();

        List<Moon> moons = this.buildMoons();

        for (int i = 0; i < 1000; i++) {
            for (Moon m : moons) {
                m.updateVelocity(moons);
            }
            for (Moon m : moons) {
                m.updatePosition();
            }
        }

        int totalEnergy = moons.stream().mapToInt(Moon::getEnergy).sum();

        this.printResult(totalEnergy);

    }

    List<Moon> buildMoons() {
        return this.getInput(INPUT_FILENAME, Solution1.class)
                .stream()
                .map(Moon::new)
                .collect(Collectors.toList());
    }

    class Moon {

        int[] position;
        int[] velocity;
        private String regExLine = ".*=(.+),.*=(.+),.*=(.+)>.*";
        private final Pattern pattern = Pattern.compile(regExLine);

        Moon(String moonData) {
            this.position = new int[3];
            this.velocity = new int[3];

            Matcher matches = pattern.matcher(moonData);

            if (matches.find()) {
                for (int i = 0; i < 3; i++) {
                    position[i] = Integer.parseInt(matches.group(i + 1));
                }
            }
        }

        void updatePosition() {
            for (int i = 0; i < 3; i++) {
                this.position[i] += this.velocity[i];
            }
        }

        void updateVelocity(List<Moon> moons) {
            moons.forEach(this::updateVelocity);
        }

        private void updateVelocity(Moon other) {

            for (int i = 0; i < 3; i++) {
                if (this.position[i] != other.position[i]) {
                    this.velocity[i] += this.position[i] > other.position[i] ? -1 : 1;
                }
            }
        }

        int getEnergy() {
            return this.getPotentialEnergy() * this.getKineticEnergy();
        }

        private int getPotentialEnergy() {
            int sum = 0;
            for (int i = 0; i < 3; i++) {
                sum += Math.abs(position[i]);
            }

            return sum;
        }

        private int getKineticEnergy() {
            int sum = 0;
            for (int i = 0; i < 3; i++) {
                sum += Math.abs(velocity[i]);
            }

            return sum;
        }

        @Override
        public int hashCode() {

            int ret = 0;

            for (int i = 0; i < 3; i++) {
                ret += Integer.hashCode(position[i]) + Integer.hashCode(velocity[i]);
            }
            return ret;
        }

        @Override
        public String toString() {
            return String.format("pos=<x= %s, y=  %s, z= %s>, vel=<x= %s, y= %s, z= %s>",
                    position[0],
                    position[1],
                    position[2],
                    velocity[0],
                    velocity[1],
                    velocity[2]);
        }
    }
}
