/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package solutions.day7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import models.ProducerConsumer;
import solutions.AbstractSolution;
import solutions.helpers.ConcurrentIntCode;

public class Solution2 extends AbstractSolution {

    private static final String INPUT_FILENAME = "Input7";
    private static final int numAmps = 5;
    private static final int[] PHASES = new int[]{5, 6, 7, 8, 9};

    public Solution2() {
        super(2, 7);
    }

    @Override
    public void run() {
        super.run();

        final ExecutorService executor = Executors.newFixedThreadPool(6);

        final int[] memory = this.getIntCodeProgram(INPUT_FILENAME, Solution2.class);

        final List<List<Integer>> phaseSettings = this.permute(PHASES);

        int maxAmp = 0;

        try {
            for (List<Integer> phases : phaseSettings) {

                final List<ProducerConsumer<Integer>> ampComms =
                        Stream.generate((Supplier<ProducerConsumer<Integer>>) ProducerConsumer::new)
                                .limit(numAmps)
                                .collect(Collectors.toList());

                List<Callable<Integer>> callable = new ArrayList<>();

                for (int i = 0; i < phases.size(); i++) {
                    ampComms.get(i).produce(phases.get(i));
                    callable.add(new ConcurrentIntCode(memory.clone(), ampComms.get(i), ampComms.get((i + 1) % 5)));
                }

                ampComms.get(0).produce(0);

                try {
                    List<Future<Integer>> futures = executor.invokeAll(callable);
                    futures.get(4).get(3, TimeUnit.MILLISECONDS);

                    maxAmp = Math.max(maxAmp, ampComms.get(0).consume());

                    // Kill all running tasks, if any.
                    futures.forEach(f -> f.cancel(true));
                } catch (ExecutionException | InterruptedException | TimeoutException e) {
                    // Do nothing
                }
            }
        } finally {
            executor.shutdownNow();
        }

        this.printResult(maxAmp);
    }
}
