/*
 * Copyright 2019 MobileIron, Inc.
 * All rights reserved.
 */

package models;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ProducerConsumer<T> {

    private final BlockingQueue<T> outputQueue;

    public ProducerConsumer() {
        outputQueue = new LinkedBlockingDeque<>();
    }

    public T consume() {
        try {
            return this.outputQueue.take();
        } catch (InterruptedException e) {
            return null;
        }
    }

    public boolean produce(T value) {
        try {
            this.outputQueue.put(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }

    public void clear() {
        outputQueue.clear();
    }

}
