/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.javatuples.Pair;

public class Solution2 extends Solution1 {



    public Solution2() {
        super(2);
        OCCUPIED_RULE = 5;
    }

    protected int checkSeats(final int i, final int j, final char[][] map, final char target) {

        int count = 0;

        final List<List<Pair<Integer, Integer>>> directions = Arrays.asList(
                this.up(i, j),
                this.down(i, j),
                this.left(i, j),
                this.right(i, j),
                this.diag_up_left(i, j),
                this.diag_up_right(i, j),
                this.diag_down_left(i, j),
                this.diag_down_right(i, j)
        );

        for (List<Pair<Integer, Integer>> dir : directions) {
            for (Pair<Integer, Integer> pair : dir) {
                char toCheck = input[pair.getValue0()][pair.getValue1()];
                if ( toCheck != FLOOR) {
                    if (toCheck == target) {
                        count++;
                    }
                    break;
                }
            }
        }


        return count;
    }

    private List<Pair<Integer, Integer>> up(int row, int column) {
        List<Pair<Integer, Integer>> result = IntStream.range(0, row).mapToObj(i -> Pair.with(i, column)).collect(Collectors.toList());
        Collections.reverse(result);
        return result;
    }

    private List<Pair<Integer, Integer>> down(int row, int column) {
        return IntStream.range(row + 1, max_row).mapToObj(i -> Pair.with(i, column)).collect(Collectors.toList());
    }

    private List<Pair<Integer, Integer>> left(int row, int column) {
        final List<Pair<Integer, Integer>> result = IntStream.range(0, column).mapToObj(j -> Pair.with(row, j)).collect(Collectors.toList());
        Collections.reverse(result);
        return result;
    }

    private List<Pair<Integer, Integer>> right(int row, int column) {
        return IntStream.range(column + 1, max_column).mapToObj(j -> Pair.with(row, j)).collect(Collectors.toList());
    }

    private List<Pair<Integer, Integer>> diag_up_left(int row, int column) {
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        for (int i = row - 1, j = column - 1; i >= 0 && j>=0 ; i--, j--) {
           result.add(Pair.with(i, j));
        }
        return result;
    }

    private List<Pair<Integer, Integer>> diag_down_right(int row, int column) {
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        for (int i = row + 1, j = column + 1; i < max_row && j < max_column ; i++, j++) {
            result.add(Pair.with(i, j));
        }
        return result;
    }

    private List<Pair<Integer, Integer>> diag_up_right(int row, int column) {
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        for (int i = row - 1, j = column + 1; i >= 0 && j < max_column ; i--, j++) {
            result.add(Pair.with(i, j));
        }
        return result;
    }

    private List<Pair<Integer, Integer>> diag_down_left(int row, int column) {
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        for (int i = row + 1, j = column - 1; i < max_row && j >= 0 ; i++, j--) {
            result.add(Pair.with(i, j));
        }
        return result;
    }

}
