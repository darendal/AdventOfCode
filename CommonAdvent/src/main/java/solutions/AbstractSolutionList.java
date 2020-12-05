/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class AbstractSolutionList implements SolutionList {

    private final List<AbstractSolution> solutionList;

    protected AbstractSolutionList(List<AbstractSolution> solutionList) {
        this.solutionList = solutionList;
    }

    @Override
    public void runAll() {
        solutionList.forEach(this::runWithTimer);
    }

    @Override
    public void runLast() {
        this.runWithTimer(this.solutionList.get(solutionList.size() - 1));
    }

    @Override
    public void runSingleDay(int day) {
        solutionList
                .stream()
                .filter(s -> s.getDayNumber() == day)
                .forEach(AbstractSolution::run);
    }

    @Override
    public void runSingleDayWithTimer(int day) {

        solutionList
                .stream()
                .filter(s -> s.getDayNumber() == day)
                .forEach(this::runWithTimer);
    }

    private void runWithTimer(final AbstractSolution solution) {
        Instant start = Instant.now();
        solution.run();
        Instant end = Instant.now();
        System.out.printf( "Execution took %s ms" , Duration.between(start, end).toMillis());
    }
}
