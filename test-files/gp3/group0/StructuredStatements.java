interface StructuredStatements {

    int x;

    default void f() {
        // If structures
        if (true) f();

        if (true) {
            f();
        }

        if (true);

        if (true) {
            f();
            f();
            f();
        }

        if (x > 0)
            System.out.println("Positive");
        else
            System.out.println("Not positive");

        if (x > 0) {
            System.out.println("Positive");
        } else;

        if (x > 0)
            System.out.println("Positive");
        else if (x < 0)
            System.out.println("Negative");
        else
            System.out.println("Zero");

        if (x > 0) {
            System.out.println("Positive");
        } else if (x < 0) {
            System.out.println("Negative");
        } else {
            System.out.println("Zero");
        }

        // Make sure the else is grouped with the right if!
        if (x > 0)
            System.out.println("Positive");
        if (x >= 0)
            System.out.println("Nonnegative");
        else
            System.out.println("Negative");

        // While loops
        while (true);
        while (true) {}
        while (x > 0) --x;
        while (x > 0) { --x; }
        while (x --> 0) { System.out.println(x); }
        do; while (true);
        do {} while (true);
        do x--; while (true);
        do { x--; } while (true);
        do { --x; } while (x > 0);

        // For loops
        for (;;);
        for (;;){}
        for (int x = 0;;);
        for (; x < 100;);
        for (;; ++x);
        for (int x = 0; x < 100; ++x);
        for (int x = 0; x < 100; ++x) {
            System.out.println(x);
        }
        for (new f(); ; f.increment()) {
            System.out.println("Something something Vicki something something");
            f();
        }

        // Nested structures

        // Get a cookie if you can tell me what this is....
        while (x != 1) {
            System.out.println(x);
            if (x & 1 == 0)
                x <<= 1;
            else
                x += (x << 1) + 1;
        }

        int n = 1000;
        boolean[] flags = new boolean[n];
        for (int i = 2; i < flags.length; ++i)
            flags[i] = true;
        for (int i = 2; 2 * i < flags.length; ++i) {
            for (int j = 2 * i; j < flags.length; j += i) {
                flags[j] = false;
            }
        }
        for (int i = 0; i < flags.length; ++i)
            if (flags[i]) System.out.printf("%d is prime.\n", i);

        for (int i = 0; i < 100; ++i) {
            if (i % 6 == 0)
                System.out.println("Fizzbuzz");
            else if (i % 2 == 0)
                System.out.println("Fizz");
            else if (i % 3 == 0)
                System.out.println("Buzz");
            else
                System.out.println(i);
        }
    }
}