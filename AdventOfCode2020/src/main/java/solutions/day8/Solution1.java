/*
 * Copyright 2020 MobileIron, Inc.
 * All rights reserved.
 */
package solutions.day8;

import java.util.List;

import solutions.AbstractSolution;

public class Solution1 extends AbstractSolution {

    public Solution1() {
        this(1);
    }

    protected Solution1(int solutionNum) {
        super(solutionNum, 8);
    }

    @Override
    public void run() {
        super.run();

        final List<String> program = this.getInput();

        this.printResult(this.runProgram(program).accumulatorValue);
    }

    protected ProgramResult runProgram(List<String> program) {

        final boolean[] visited = new boolean[program.size()];

        int accumulator = 0;
        for (int i = 0; i < program.size(); i++) {

            if (visited[i]) {
                return new ProgramResult(false, accumulator);
            } else {
                visited[i] = true;
            }

            final String[] instruction = program.get(i).split(" ");
            switch (instruction[0]) {
                case "acc":
                    accumulator += Integer.parseInt(instruction[1]);
                    break;
                case "jmp":
                    i += Integer.parseInt(instruction[1]) - 1;
            }
        }

        return new ProgramResult(true, accumulator);
    }

    protected static class ProgramResult {
        protected boolean success;
        protected int accumulatorValue;

        public ProgramResult(boolean success, int accumulatorValue) {
            this.success = success;
            this.accumulatorValue = accumulatorValue;
        }
    }
}
