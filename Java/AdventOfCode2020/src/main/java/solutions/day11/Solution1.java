/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day11;

import java.util.List;

import org.javatuples.Pair;

import solutions.AbstractSolution;

public class Solution1 extends AbstractSolution {

    protected static int max_row;
    protected static int max_column;

    protected static final char FLOOR = '.';
    protected static final char OCCUPIED = '#';
    protected static final char EMPTY = 'L';

    protected static int OCCUPIED_RULE = 4;


    protected char[][] input;
    public Solution1() {
        this(1);
    }

    protected Solution1(int solutionNumber){
        super(solutionNumber, 11);
    }

    @Override
    public void run() {
        super.run();

        final List<String> inputList = this.getInput();
        max_row = inputList.size();
        max_column = inputList.get(0).length();

        input = new char[max_row][max_column];
        for (int i = 0; i < inputList.size(); i++) {
            String value = inputList.get(i);
            if (!value.equals("")) {
                input[i] = inputList.get(i).toCharArray();
            }
        }
        int count;
        do {
            //this.printMap(input);
            Pair<Integer, char[][]> result = runSimulation(input);
            count = result.getValue0();
            input = result.getValue1();
        } while (count != 0);

        int numOcc = 0;
        for (int i = 0; i < max_row; i++) {
            for (int j = 0; j < max_column; j++) {
                if (input[i][j] == OCCUPIED) {
                    numOcc++;
                }
            }
        }

        this.printResult(numOcc);
    }

    private Pair<Integer, char[][]> runSimulation(char[][] seatMap) {

        char[][] end = new char[max_row][max_column];

        int count = 0;
        for (int i = 0; i < max_row; i++) {
            for (int j = 0; j < max_column; j++) {
               char seat = seatMap[i][j];
               final char endChar;
               if (seat == EMPTY && checkSeats(i, j, seatMap, OCCUPIED) == 0) {
                   endChar = OCCUPIED;
                   count++;
               } else if (seat == OCCUPIED) {
                   if (checkSeats(i, j, seatMap, OCCUPIED) >= OCCUPIED_RULE) {
                       endChar = EMPTY;
                       count++;
                   } else {
                       // Remains unchanged.
                       endChar = seat;
                   }
               } else {
                   // Remains unchanged.
                   endChar = seat;
               }
               end[i][j] = endChar;
            }
        }

        return Pair.with(count, end);
    }

    protected int checkSeats(final int i, final int j, final char[][] map, final char target) {
        int count = 0;
        for (int row = i - 1; row <= i + 1; row++) {
            if (row >= 0 && row < max_row) {
                for (int column = j - 1; column <= j + 1; column++) {
                    if (row == i && column == j) {
                        continue;
                    }
                    else if ((column >= 0 && column < max_column) && map[row][column] == target) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private void printMap(char[][] map) {
        for (int i = 0; i < max_row; i++) {
            System.out.println(String.valueOf(map[i]));
        }
        System.out.println();

    }
}
