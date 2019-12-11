/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.helpers;

import java.util.concurrent.Callable;

import models.ProducerConsumer;

public class ConcurrentIntCode extends IntCode implements Callable<Integer> {

    private ProducerConsumer<Long> input;
    private ProducerConsumer<Long> output;

    public ConcurrentIntCode(long[] program, final ProducerConsumer<Long> input, final ProducerConsumer<Long> output) {
        super(program);

        this.input = input;
        this.output = output;
    }

    @Override
    void input(int startPosition) {

        final long inputValue = this.input.consume();

        long index = this.getAddress(this.getValueFromAddress(startPosition), startPosition + 1, 1);

        this.writeToAddress(index, inputValue);

        programCounter += 2;
    }

    @Override
    void output(int startPosition) {
        long index = getValues(program[programCounter], new long[]{program[startPosition + 1]})[0];

        this.output.produce(index);

        programCounter += 2;
    }

    @Override
    public Integer call() {
        this.processIntcodeProgram();

        return 0;
    }
}
