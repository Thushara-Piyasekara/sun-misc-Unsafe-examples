package org.ByteReader;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class ByteReader {

    public static Unsafe unSafe;

    public static Unsafe getUnsafe()
            throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        return unsafe;
    }

    public static void normalLookup(byte[] byteArr) {
        for (int i = 0; i < byteArr.length; ++i) {
            if (0 == byteArr[i]) {
                System.out.println("The 1st '0' is at position : " + i);
                return;
            }
        }
        System.out.println("Not found '0'");
    }

    public static void unsafeLookup(byte[] byteArr) {
        int baseOffset = unSafe.arrayBaseOffset(byte[].class);
        for (int i = 0; i < byteArr.length; ++i) {
            byte b = unSafe.getByte(byteArr, (baseOffset + i));
            if (0 == b) {
                System.out.println("The 1st '0' is at position : " + i);
                return;
            }
        }
        System.out.println("Not found '0'");
    }

    public static void main(String[] args)
            throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {

        unSafe = getUnsafe();

        int len = 1024 * 1024;
        long startTime = 0L;
        long endTime = 0L;

        System.out.println("initialize data...");
        byte[] byteArray = new byte[len];
        for (int i = 0; i < len; ++i) {
            byteArray[i] = (byte) (i % 128 + 1);  //No byte will equal to 0
        }

        byteArray[25000] = (byte) 0;

        System.out.println("initialize data done!");

        System.out.println("use normalLookup()...");
        startTime = System.nanoTime();

        normalLookup(byteArray);

        endTime = System.nanoTime();
        System.out.println("time : " + ((endTime - startTime)) + " ns");

        System.out.println("use unsafeLookup()...");
        startTime = System.nanoTime();

        unsafeLookup(byteArray);

        endTime = System.nanoTime();
        System.out.println("time : " + (endTime - startTime) + " ns");
    }

}
