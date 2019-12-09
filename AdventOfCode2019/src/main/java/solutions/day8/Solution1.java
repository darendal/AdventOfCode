/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day8;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import solutions.AbstractSolution;

public class Solution1 extends AbstractSolution {

    private static final String INPUT_FILENAME = "Input8";
    private static final int WIDTH = 25;
    private static final int HEIGHT = 6;

    public Solution1() {
        this(1);
    }

    Solution1(int solutionNumber) {
        super(solutionNumber, 8);
    }

    @Override
    public void run() {
        super.run();

        List<Layer> layers = this.buildLayers();

        Layer minZero = layers.stream().min(Comparator.comparing(l -> l.getCount(0))).get();

        this.printResult(minZero.getCount(1) * minZero.getCount(2));

    }

    List<Layer> buildLayers() {
        char[] input = this.getInput(INPUT_FILENAME, Solution1.class).get(0).toCharArray();
        List<Layer> layers = new ArrayList<>();

        final int layerLength = WIDTH * HEIGHT;

        for (int i = 0; i < input.length; i += layerLength) {
            Layer l = new Layer(WIDTH, HEIGHT);
            for (int j = i; j < i + layerLength; j++) {
                l.insert(input[j]);
            }
            layers.add(l);
        }

        return layers;
    }

    class Layer {

        private static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
        private static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
        private static final String ANSI_RESET = "\u001B[0m";

        private static final String TEMPLATE = "%s %s";

        private int width;
        private int height;
        private int[][] layer;

        private int[] countMap;

        private int currentIndexX;
        private int currentIndexY;

        Layer(int width, int height, int[] layer) {
            this(width, height);

            for (int i : layer) {
                this.insert(i);
            }
        }

        Layer(int width, int height) {
            this.width = width;
            this.height = height;
            countMap = new int[10];

            this.layer = new int[height][width];
            this.currentIndexX = 0;
            this.currentIndexY = 0;
        }

        void insert(int i) {
            countMap[i]++;

            if (this.currentIndexX >= this.width) {
                this.currentIndexY++;
                this.currentIndexX = 0;
            }

            this.layer[currentIndexY][currentIndexX] = i;
            currentIndexX++;
        }

        void insert(char c) {
            countMap[c - '0']++;

            if (this.currentIndexX >= this.width) {
                this.currentIndexY++;
                this.currentIndexX = 0;
            }

            this.layer[currentIndexY][currentIndexX] = c - '0';
            currentIndexX++;
        }

        int getCount(int value) {
            if (value < 0 || value > 9) {
                return -1;
            } else {
                return countMap[value];
            }
        }

        Layer add(Layer other) {

            int[] result = new int[this.width * this.height];

            int counter = 0;

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (this.layer[i][j] == 2) {
                        result[counter++] = other.layer[i][j];
                    } else {
                        result[counter++] = this.layer[i][j];
                    }
                }
            }

            return new Layer(this.width, this.height, result);
        }

        void print() {
            for (int i = 0; i < this.height; i++) {
                for (int j = 0; j < this.width; j++) {
                    System.out.print(String.format(TEMPLATE,
                            this.layer[i][j] == 0 ? ANSI_BLACK_BACKGROUND : ANSI_WHITE_BACKGROUND,
                            ANSI_RESET));
                }
                System.out.println();
            }
        }
    }
}
