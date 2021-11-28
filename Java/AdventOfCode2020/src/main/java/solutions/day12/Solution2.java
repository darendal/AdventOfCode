/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day12;

import solutions.AbstractSolution;

public class Solution2 extends AbstractSolution {

    private final char[] DIRECTIONS = new char[]{'N', 'E', 'S', 'W'};

    private int facing = 1;
    private int north_south = 0;
    private int east_west = 0;
    private int waypoint_east_west = 10;
    private int waypoint_north_south = 1;

    public Solution2() {
        super(2, 12);
    }

    @Override
    public void run() {
        super.run();

    }

    private void makeMove(String instructionString) {

        char instruction = instructionString.charAt(0);
        int value = Integer.parseInt(instructionString.substring(1));

        switch (instruction) {
            case 'F':
                move(value);
                break;
            case 'L':
                rotate_left(value);
                break;
            case 'R':
                rotate_right(value);
                break;
            default:
                move_waypoint(instruction, value);
                break;
        }
    }

    private void move(int value) {

    }

    private void move_waypoint(char direction, int value) {
        switch (direction) {
            case 'N':
                this.waypoint_north_south += value;
                break;
            case 'S':
                this.waypoint_north_south -= value;
                break;
            case 'E':
                this.waypoint_east_west += value;
                break;
            case 'W':
                this.waypoint_east_west -= value;
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
