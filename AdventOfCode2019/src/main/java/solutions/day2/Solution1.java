/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day2;

import java.util.Arrays;

import solutions.AbstractSolution;

public class Solution1 extends AbstractSolution {

    private static final String INPUT_FILENAME = "Input2";

    public Solution1() {
        this(1);
    }

    Solution1(int solutionNumber) {
        super(solutionNumber, 2);
    }

    @Override
    public void run() {
        super.run();

        int[] program = getProgram();

        program[1] = 12;
        program[2] = 2;

        int[] result = this.processIntcodeProgram(program);

        this.printResult(result[0]);

    }

    int[] getProgram() {
        String input = this.getInput(INPUT_FILENAME).get(0);
        return Arrays.stream(input.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    int[] processIntcodeProgram(int[] program) {
        int programCounter = 0;

        while (true) {
            switch (program[programCounter]) {
                case 1:
                    addOpCode(program, programCounter);
                    break;
                case 2:
                    multiplyOpCode(program, programCounter);
                    break;
                case 99:
                    // halt execution
                    return program;
                default:
                    throw new IllegalArgumentException("Encountered invalid opcode at position " + programCounter);
            }
            programCounter += 4;
        }
    }

    private int[] addOpCode(int[] program, int startPosition) {
        int value1 = program[startPosition + 1];
        int value2 = program[startPosition + 2];
        int index = program[startPosition + 3];

        program[index] = program[value1] + program[value2];

        return program;
    }

    private int[] multiplyOpCode(int[] program, int startPosition) {
        int value1 = program[startPosition + 1];
        int value2 = program[startPosition + 2];
        int index = program[startPosition + 3];

        program[index] = program[value1] * program[value2];

        return program;
    }
}
