package org.Counters;

public class NormalCounter implements Counter{

    private int counter = 0;

    @Override
    public synchronized int increment() {
        return counter = counter + 1;
    }

    @Override
    public synchronized int get() {
        return counter;
    }
}
