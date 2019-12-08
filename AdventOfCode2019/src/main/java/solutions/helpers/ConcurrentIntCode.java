/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.helpers;

import java.util.concurrent.Callable;

import models.ProducerConsumer;

public class ConcurrentIntCode extends IntCode implements Callable<Integer> {

    private ProducerConsumer<Integer> input;
    private ProducerConsumer<Integer> output;

    public ConcurrentIntCode(int[] program, final ProducerConsumer<Integer> input, final ProducerConsumer<Integer> output) {
        super(program);

        this.input = input;
        this.output = output;
    }

    @Override
    void input(int startPosition) {
        int inputValue = 0;
        try {

            inputValue = this.input.consume();
        } catch (NullPointerException e) {
            program[startPosition] = 99;
            return;
        }

        int index = this.program[startPosition + 1];

        this.program[index] = inputValue;

        programCounter += 2;
    }

    @Override
    void output(int startPosition) {
        int index = getValues(program[programCounter], new int[]{program[startPosition + 1]})[0];

        this.output.produce(index);

        programCounter += 2;
    }

    @Override
    public Integer call() throws Exception {
        this.processIntcodeProgram();

        return 0;
    }
}
