/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class IntCode {

    private int[] program;
    private Stack<Integer> input;
    private int programCounter;

    public IntCode(int[] program, int[] input) {
        this(program);

        for (int i : input) {
            this.input.push(i);
        }
    }

    public IntCode(int[] program) {
        this.program = program;
        this.programCounter = 0;
        this.input = new Stack<>();
    }

    public int[] processIntcodeProgram() {

        while (true) {
            switch (program[programCounter] % 100) {
                case 1:
                    addOpCode(programCounter);
                    break;
                case 2:
                    multiplyOpCode(programCounter);
                    break;
                case 3:
                    input(programCounter);
                    break;
                case 4:
                    output(programCounter);
                    break;
                case 5:
                    jumpIfTrue(programCounter);
                    break;
                case 6:
                    jumpIfFalse(programCounter);
                    break;
                case 7:
                    lessThan(programCounter);
                    break;
                case 8:
                    equals(programCounter);
                    break;
                case 99:
                    // halt execution
                    return program;
                default:
                    throw new IllegalArgumentException("Encountered invalid opcode at position " + programCounter);
            }
        }
    }

    public int[] processIntcodeProgram(int noun, int verb) {
        program[1] = noun;
        program[2] = verb;

        return processIntcodeProgram();
    }

    private void addOpCode(int startPosition) {

        int[] values = this.getValues(
                program[programCounter],
                new int[]{
                        program[startPosition + 1],
                        program[startPosition + 2]
                }
        );

        int value1 = values[0];
        int value2 = values[1];
        int index = this.program[startPosition + 3];

        program[index] = value1 + value2;

        programCounter += 4;
    }

    private void multiplyOpCode(int startPosition) {
        int[] values = this.getValues(
                program[programCounter],
                new int[]{
                        program[startPosition + 1],
                        program[startPosition + 2]
                }
        );

        int value1 = values[0];
        int value2 = values[1];
        int index = this.program[startPosition + 3];

        program[index] = value1 * value2;

        programCounter += 4;
    }

    private void input(int startPosition) {

        int inputValue = this.input.pop();
        int index = this.program[startPosition + 1];

        this.program[index] = inputValue;

        programCounter += 2;
    }

    private void output(int startPosition) {

        int index = getValues(program[programCounter], new int[]{program[startPosition + 1]})[0];
        System.out.println(index);

        programCounter += 2;
    }

    private void jumpIfTrue(int startPosition) {
        final int[] values = this.getValues(program[programCounter], new int[]{
                program[startPosition + 1],
                program[startPosition + 2]
        });

        if (values[0] != 0) {
            this.programCounter = values[1];
        } else {
            programCounter += 3;
        }
    }

    private void jumpIfFalse(int startPosition) {
        final int[] values = this.getValues(program[programCounter], new int[]{
                program[startPosition + 1],
                program[startPosition + 2]
        });

        if (values[0] == 0) {
            this.programCounter = values[1];
        } else {
            programCounter += 3;
        }
    }

    private void lessThan(int startPosition) {
        final int[] values = this.getValues(program[programCounter], new int[]{
                program[startPosition + 1],
                program[startPosition + 2]
        });

        int index = program[startPosition + 3];

        program[index] = values[0] < values[1] ? 1 : 0;
        programCounter += 4;
    }

    private void equals(int startPosition) {
        final int[] values = this.getValues(program[programCounter], new int[]{
                program[startPosition + 1],
                program[startPosition + 2]
        });

        int index = program[startPosition + 3];

        program[index] = values[0] == values[1] ? 1 : 0;

        programCounter += 4;
    }

    private int[] getValues(int opCode, int[] parameters) {

        char[] paramMode = (opCode + "").toCharArray();

        List<Integer> results = new ArrayList<>(parameters.length);

        for (int i = 0; i < parameters.length; i++) {

            int paramModeIndex = paramMode.length - 3 - i;

            int value;
            if (paramModeIndex < 0 || paramMode[paramModeIndex] == '0') {
                value = getValueFromAddress(parameters[i]);
            } else {
                value = parameters[i];
            }
            results.add(value);
        }

        return results.stream().mapToInt(i -> i).toArray();
    }

    private int getValueFromAddress(int address) {
        return program[address];
    }
}
