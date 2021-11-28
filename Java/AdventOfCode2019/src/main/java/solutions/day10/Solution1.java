/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day10;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import solutions.AbstractSolution;

public class Solution1 extends AbstractSolution {

    private static final String INPUT_FILENAME = "Input10";

    public Solution1() {
        this(1);
    }

    Solution1(int solutionNumber) {
        super(solutionNumber, 10);
    }

    @Override
    public void run() {
        super.run();

        List<Asteroid> asteroids = this.buildAsteroidList();

        asteroids.forEach(origin -> origin.populateMap(asteroids));

        int result = asteroids.stream().mapToInt(Asteroid::visibleAsteroids).max().getAsInt();

        this.printResult(result);
    }

    List<Asteroid> buildAsteroidList() {
        List<Asteroid> asteroids = new ArrayList<>();

        List<String> input = this.getInput(INPUT_FILENAME, Solution1.class);

        for (int i = 0; i < input.size(); i++) {
            char[] line = input.get(i).toCharArray();
            for (int j = 0; j < line.length; j++) {
                if (line[j] == '#') {
                    asteroids.add(new Asteroid(j, i));
                }
            }
        }

        return asteroids;
    }

    class Asteroid {
        int x;
        int y;

        private Map<Double, List<Asteroid>> asteroidMap;

        Asteroid(int x, int y) {
            this.x = x;
            this.y = y;
            this.asteroidMap = new HashMap<>();
        }

        void populateMap(List<Asteroid> asteroidList) {
            asteroidList.forEach(this::addAsteroid);

            for (List<Asteroid> slopeList : this.asteroidMap.values()) {
                slopeList.add(this); //this is important to figure out which ones in this slope the asteroid can find.
                sortSlopeList(slopeList);
            }
        }

        private void addAsteroid(Asteroid asteroid) {
            if (!this.equals(asteroid)) {
                double slope = ((double) (this.y - asteroid.y)) / (this.x - asteroid.x);
                if (slope == Double.POSITIVE_INFINITY) {
                    slope = Double.NEGATIVE_INFINITY; //this doesnt matter but helps for part 2
                }

                List<Asteroid> slopes = this.asteroidMap.getOrDefault(slope, new ArrayList<>());
                slopes.add(asteroid);
                this.asteroidMap.putIfAbsent(slope, slopes);
            }
        }

        private void sortSlopeList(List<Asteroid> slopList) {
            slopList.sort((o1, o2) -> {
                if (o1.x != o2.x) {
                    return Integer.compare(o1.x, o2.x);
                } else {
                    return Integer.compare(o1.y, o2.y);
                }
            });
        }

        int visibleAsteroids() {
            int countOfAsteroidsThatCanBeSeen = 0;
            for (List<Asteroid> asteroids : this.asteroidMap.values()) {

                int indexOfThisInSlopeList = asteroids.indexOf(this);
                if (indexOfThisInSlopeList > 0) {
                    countOfAsteroidsThatCanBeSeen++;
                }
                if (indexOfThisInSlopeList < asteroids.size() - 1) {
                    countOfAsteroidsThatCanBeSeen++;
                }
            }
            return countOfAsteroidsThatCanBeSeen;
        }

        Collection<Asteroid> destroyVisibleAsteroid() {
            List<Double> sortedKeys = this.asteroidMap
                    .keySet()
                    .stream()
                    .sorted(Comparator.comparingDouble(o -> o))
                    .collect(Collectors.toList());

            Stack<Asteroid> destroyed = new Stack<>();

            boolean beneath = false;
            boolean done = false;
            while (!done) {

                done = true;
                for (double key : sortedKeys) {
                    List<Asteroid> asteroidsInSlope = this.asteroidMap.get(key);
                    int thisIndex = asteroidsInSlope.indexOf(this);

                    if (beneath) {
                        if (thisIndex != 0) {
                            Asteroid removed = asteroidsInSlope.remove(thisIndex - 1);
                            destroyed.push(removed);
                        }
                    } else {
                        if (thisIndex != asteroidsInSlope.size() - 1) {
                            Asteroid removed = asteroidsInSlope.remove(thisIndex + 1);
                            destroyed.push(removed);
                        }
                    }
                    if (asteroidsInSlope.size() > 1) {
                        done = false;
                    }
                }

                beneath = !beneath;
            }

            return destroyed;
        }

        @Override
        public String toString() {
            return "(" + this.x + ", " + this.y + ")";
        }
    }

}
