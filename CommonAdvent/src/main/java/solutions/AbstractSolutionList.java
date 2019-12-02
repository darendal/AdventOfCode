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
        solutionList.forEach(AbstractSolution::run);
    }

    @Override
    public void runLast() {
        this.solutionList.get(solutionList.size() - 1).run();
    }

    @Override
    public void runSingleDay(int day) {

    }
}
