/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day6;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import solutions.AbstractSolution;

public class Solution1 extends AbstractSolution {

    public Solution1() {
        super(1, 6);
    }

    @Override
    public void run() {
        super.run();

        final List<String> input = this.getInput();

        Set<Character> answers = new HashSet<>();

        int summation = 0;

        for (String person : input) {
            if (person.equals("")) {
                summation += answers.size();
                answers.clear();
            }
            answers.addAll(person.chars().mapToObj(c -> (char)c).collect(Collectors.toList()));
        }

        this.printResult(summation);
    }
}
