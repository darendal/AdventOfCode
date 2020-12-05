/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions;

public interface SolutionList {

    void runAll();

    void runLast();

    void runSingleDay(int day);

    void runSingleDayWithTimer(int day);
}
