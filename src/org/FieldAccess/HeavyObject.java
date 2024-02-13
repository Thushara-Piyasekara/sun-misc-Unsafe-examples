package org.FieldAccess;

class HeavyObject {

    private int foo;
    private String bar;

    public HeavyObject(int foo, String bar) {
        this.foo = foo;
        this.bar = bar;
        heavyCalculation();
    }

    private static void heavyCalculation() {
        System.out.println("Doing heavy calculation...");
        try {
            Thread.sleep(5000);
            System.out.println("Heavy calculation done...");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void printHeavyStuff() {
        System.out.println(String.format("foo: %s\nbar: %s", foo, bar));
    }
}
