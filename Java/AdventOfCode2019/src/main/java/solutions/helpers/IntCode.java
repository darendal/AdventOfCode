/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class IntCode {

    long[] program;
    int programCounter;

    private Stack<Long> input;
    private Stack<Long> output;
    private Map<Long, Long> virtualMemoryMap;
    private long relativeBase;


    public IntCode(long[] program, long[] input) {
        this(program);

        for (long i : input) {
            this.input.push(i);
        }
    }

    public IntCode(long[] program) {
        this.program = program;
        this.programCounter = 0;
        this.input = new Stack<>();
        this.output = new Stack<>();

        this.relativeBase = 0;

        this.virtualMemoryMap = new HashMap<>();
    }

    public long[] processIntcodeProgram() {

        while (true) {
            switch ((int) (program[programCounter] % 100)) {
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
                case 9:
                    relativeOffset(programCounter);
                    break;
                case 99:
                    // halt execution
                    return program;
                default:
                    throw new IllegalArgumentException("Encountered invalid opcode at position " + programCounter);
            }
        }
    }

    public long[] processIntcodeProgram(int noun, int verb) {
        program[1] = noun;
        program[2] = verb;

        return processIntcodeProgram();
    }

    public Stack<Long> getOutput() {
        return this.output;
    }

    private void addOpCode(int startPosition) {

        long[] values = this.getValues(
                program[programCounter],
                new long[]{
                        program[startPosition + 1],
                        program[startPosition + 2],
                }
        );

        long value1 = values[0];
        long value2 = values[1];
        long index = this.getAddress(program[programCounter], startPosition + 3, 3);

        this.writeToAddress(index, value1 + value2);

        programCounter += 4;
    }

    private void multiplyOpCode(int startPosition) {
        long[] values = this.getValues(
                program[programCounter],
                new long[]{
                        program[startPosition + 1],
                        program[startPosition + 2],
                }
        );

        long value1 = values[0];
        long value2 = values[1];
        long index = getAddress(program[programCounter], startPosition + 3, 3);

        this.writeToAddress(index, value1 * value2);

        programCounter += 4;
    }

    void input(int startPosition) {

        long inputValue = this.input.pop();
        long index = this.getAddress(this.getValueFromAddress(startPosition), startPosition + 1, 1);

        this.writeToAddress(index, inputValue);

        programCounter += 2;
    }

    void output(int startPosition) {

        long index = getValues(program[programCounter], new long[]{program[startPosition + 1]})[0];

        this.output.push(index);

        programCounter += 2;
    }

    private void jumpIfTrue(int startPosition) {
        final long[] values = this.getValues(program[programCounter], new long[]{
                program[startPosition + 1],
                program[startPosition + 2]
        });

        if (values[0] != 0) {
            this.programCounter = (int) values[1];
        } else {
            programCounter += 3;
        }
    }

    private void jumpIfFalse(int startPosition) {
        final long[] values = this.getValues(program[programCounter], new long[]{
                program[startPosition + 1],
                program[startPosition + 2]
        });

        if (values[0] == 0) {
            this.programCounter = (int) values[1];
        } else {
            programCounter += 3;
        }
    }

    private void lessThan(int startPosition) {
        final long[] values = this.getValues(program[programCounter], new long[]{
                program[startPosition + 1],
                program[startPosition + 2],
        });

        long index = this.getAddress(program[programCounter], startPosition + 3, 3);

        this.writeToAddress(index, values[0] < values[1] ? 1 : 0);

        programCounter += 4;
    }

    private void equals(int startPosition) {
        final long[] values = this.getValues(program[programCounter], new long[]{
                program[startPosition + 1],
                program[startPosition + 2],
        });

        long index = getAddress(program[programCounter], startPosition + 3, 3);

        this.writeToAddress(index, values[0] == values[1] ? 1 : 0);

        programCounter += 4;
    }

    private void relativeOffset(int startPosition) {
        final long[] values = this.getValues(program[startPosition], new long[]{
                program[startPosition + 1],
        });

        this.relativeBase += values[0];

        this.programCounter += 2;
    }

    long[] getValues(long opCode, long[] parameters) {

        char[] paramMode = (opCode + "").toCharArray();

        List<Long> results = new ArrayList<>(parameters.length);

        for (int i = 0; i < parameters.length; i++) {

            int paramModeIndex = paramMode.length - 3 - i;

            long value;
            if (paramModeIndex < 0 || paramMode[paramModeIndex] == '0') { // position mode
                value = getValueFromAddress(parameters[i]);
            } else if (paramMode[paramModeIndex] == '1') { // Immediate mode
                value = parameters[i];
            } else if (paramMode[paramModeIndex] == '2') { // Relative mode
                value = getValueFromAddress(parameters[i] + relativeBase);
            } else {
                throw new IllegalArgumentException("Unknown parameter mode received: " + paramMode[paramModeIndex]);
            }
            results.add(value);
        }

        return results.stream().mapToLong(i -> i).toArray();
    }

    void writeToAddress(long address, long value) {
        if (address >= program.length) {
            this.virtualMemoryMap.put(address, value);
        } else {
            this.program[(int) address] = value;
        }
    }

    long getAddress(long opCode, long position, long paramCount) {

        char[] paramMode = (opCode + "").toCharArray();


        int paramModeIndex = (int) (paramMode.length - 3 - paramCount + 1);

        if (paramModeIndex < 0 || paramMode[paramModeIndex] == '0') {
            return getValueFromAddress(position);
        } else if (paramMode[paramModeIndex] == '2') {
            return this.getValueFromAddress(position) + this.relativeBase;
        } else {
            throw new IllegalArgumentException("Unknown parameter mode received: " + paramMode[paramModeIndex]);
        }
    }

    long getValueFromAddress(long address) {
        if (address >= program.length) {
            return this.virtualMemoryMap.getOrDefault(address, 0L);
        } else {
            return this.program[(int) address];
        }
    }

}
