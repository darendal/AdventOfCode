/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

import solutions.SolutionList;
import solutions.Solutions2020;

public class AdventRunner {

    public static void main(String[] args) {

        final SolutionList solutionList = new Solutions2020();
        //final SolutionList solutionList = new Solutions2019();
        //final SolutionList solutionList = new Solutions2018();

        solutionList.runAll();
    }
}
