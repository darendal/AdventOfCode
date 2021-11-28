/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day12;

import solutions.AbstractSolution;
import utils.CommonUtils;

public class Solution1 extends AbstractSolution {

    private int facing = 1;
    private int north_south = 0;
    private int east_west = 0;

    private final char[] DIRECTIONS = new char[]{'N', 'E', 'S', 'W'};

    public Solution1() {
        super(1, 12);
    }

    @Override
    public void run() {
        super.run();

        this.getInputStream().forEach(this::makeMove);

        int result = CommonUtils.manhattanDistance(0, 0, this.east_west, this.north_south);

        this.printResult(result);
    }

    private void makeMove(String instructionString) {

        char instruction = instructionString.charAt(0);
        int value = Integer.parseInt(instructionString.substring(1));

        switch (instruction) {
            case 'F':
                move(this.DIRECTIONS[this.facing], value);
                break;
            case 'L':
                rotate_left(value);
                break;
            case 'R':
                rotate_right(value);
                break;
            default:
                move(instruction, value);
                break;
        }
    }

    private void move(char direction, int value) {
        switch (direction) {
            case 'N':
                this.north_south += value;
                break;
            case 'S':
                this.north_south -= value;
                break;
            case 'E':
                this.east_west += value;
                break;
            case 'W':
                this.east_west -= value;
        }
    }

    private void rotate_left(int value) {
        int result = (value / 90);
        this.facing =  Math.floorMod((this.facing-result), 4);
    }

    private void rotate_right(int value) {
        int result = (value / 90);
        this.facing =  Math.floorMod(this.facing + result, 4);
    }
}
