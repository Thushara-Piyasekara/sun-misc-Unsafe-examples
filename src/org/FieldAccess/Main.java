package org.FieldAccess;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Main {

    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        // Initializing the Unsafe
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);
        
        /*
        /////////////////////////////////////////////////////////////
        1. Accessing private fields
        ////////////////////////////////////////////////////////////
        */

        SecretAgent secretAgent = new SecretAgent(007);
        Field secretField = secretAgent.getClass().getDeclaredField("secretNumber");

        long secretFieldOffset = unsafe.objectFieldOffset(secretField);
        System.out.println("Old secretNumber field : " + secretAgent.getSecretName());

        unsafe.putInt(secretAgent, secretFieldOffset, 42);

        System.out.println("New secretNumber field : " + secretAgent.getSecretName());

        /*
        /////////////////////////////////////////////////////////////
        2. Instantiating Objects without invoking the constructor
        ////////////////////////////////////////////////////////////
        */

        HeavyObject unsafeHeavyObject = (HeavyObject) unsafe.allocateInstance(HeavyObject.class);   // Constructor won't be triggered
        unsafeHeavyObject.printHeavyStuff();

        /*
        /////////////////////////////////////////////////////////////
        3. Direct memory access
        ////////////////////////////////////////////////////////////
        */

        long memoryAddress = unsafe.allocateMemory(8);
        unsafe.putLong(memoryAddress, 6729922);
        System.out.println(unsafe.getLong(memoryAddress));
        unsafe.freeMemory(memoryAddress);   // Have to free the memory manually

    }
}
