/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions;

import java.util.List;

public class AbstractSolutionList implements SolutionList {

    private final List<AbstractSolution> solutionList;

    protected AbstractSolutionList(List<AbstractSolution> solutionList) {
        this.solutionList = solutionList;
    }

    @Override
    public void runAll() {
        long startTime = System.currentTimeMillis();
        solutionList.forEach(AbstractSolution::run);
        System.out.println("\nTime taken : " + (System.currentTimeMillis() - startTime) + " millisecond(s).");
    }

    @Override
    public void runLast() {

        this.solutionList.get(solutionList.size() - 1).run();
    }

    @Override
    public void runSingleDay(int day) {
        solutionList.stream().filter(s -> s.getDayNumber() == day).forEach(AbstractSolution::run);
    }
}
