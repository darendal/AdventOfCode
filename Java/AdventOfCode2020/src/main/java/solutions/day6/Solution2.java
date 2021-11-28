/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import solutions.AbstractSolution;

public class Solution2 extends AbstractSolution {

    public Solution2() {
        super(2, 6);
    }

    @Override
    public void run() {
        super.run();

        final List<String> input = this.getInput();

        List<Set<Character>> answers = new ArrayList<>();

        int summation = 0;

        for (String person : input) {
            if (person.equals("")) {
                summation += answers.stream()
                        .reduce((agg, elem) -> {
                            agg.retainAll(elem);
                            return agg;
                        }).orElseGet(Collections::emptySet).size();
                answers.clear();
            } else {
                answers.add(person.chars().mapToObj(c -> (char) c).collect(Collectors.toSet()));
            }
        }

        this.printResult(summation);
    }
}
