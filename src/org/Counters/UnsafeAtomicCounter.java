package org.Counters;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeAtomicCounter implements Counter {
    private static final Unsafe unsafe;
    private static final long valueOffset;
    private volatile int value = 0;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
            valueOffset = unsafe.objectFieldOffset
                    (UnsafeAtomicCounter.class.getDeclaredField("value"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int increment() {
        return unsafe.getAndAddInt(this, valueOffset, 1) + 1;
    }

    @Override
    public int get() {
        return unsafe.getInt(this, valueOffset);
    }
}
