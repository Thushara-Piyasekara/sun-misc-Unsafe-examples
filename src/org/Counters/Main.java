package org.Counters;

public class Main {

    public static void main(String[] args) {
        Counter normalCounter = new NormalCounter();
        Counter unsafeCounter = new UnsafeAtomicCounter();

        long startTimeNormal = System.nanoTime();

        while (normalCounter.get() < 10000000) {
            normalCounter.increment();
        }

        long endTimeNormal = System.nanoTime();

        System.out.println("Normal iteration time : " + ((endTimeNormal - startTimeNormal)/1000000) + "ms");

        long startTimeUnsafe = System.nanoTime();
        while (unsafeCounter.get() < 10000000) {
            unsafeCounter.increment();
        }

        long endTimeUnsafe = System.nanoTime();
        System.out.println("Unsafe iteration time : " + ((endTimeUnsafe - startTimeUnsafe)/1000000) + "ms");
    }
}