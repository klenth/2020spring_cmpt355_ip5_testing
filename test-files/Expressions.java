package test;

public final class Expressions {

    public static void main(String[] args) {
        System.out.println("Hello, world!");

        System.out.println("Literals!");
        System.out.println(65);
        System.out.println(256000000000L);
        System.out.println(1.234f);
        System.out.println(1.234567890123);
        System.out.println('X');
        System.out.println(true);

        // Unary operators
        System.out.println("Unary operators!");
        System.out.println(-65);
        System.out.println(-256000000000L);
        System.out.println(-1.234f);
        System.out.println(-1.234567890123);
        System.out.println(-'X');

        System.out.println(~0);
        System.out.println(~0L);
        System.out.println(~(byte)0);

        System.out.println(!true);
        System.out.println(!false);

        // Binary operators
        System.out.println(5 + 20);
        System.out.println(1 + 2 * 3);
        System.out.println(1 + (2 * 3));
        System.out.println((1 + 2) * 3);
        System.out.println(9 / 5);
        System.out.println(9. / 5);
        System.out.println(9 / 5.);
        System.out.println(9. / 5.);
        System.out.println("Will this really " + "work?");
        System.out.println("The answer is " + 40 + 2);
        System.out.println("My initial is " + 'K');
        System.out.println("Social isolation is a good thing? " + false);

        System.out.println(1 << 10); // 1024
        System.out.println(256 >> 4); // 16
        System.out.println(-256 >> 4L); // -16
        System.out.println(-256L >>> 4); // 1152921504606846960

        System.out.println(0 == 0);
        System.out.println(Math.PI > 3);
        System.out.println(5f >= 6f);

        System.out.println(System.out != System.out);
        System.out.println(System.out == System.err);

        System.out.println("AND truth table");
        System.out.println(false && false);
        System.out.println(false && true);
        System.out.println(true && false);
        System.out.println(true && true);

        System.out.println("OR truth table");
        System.out.println(false || false);
        System.out.println(false || true);
        System.out.println(true || false);
        System.out.println(true || true);

        System.out.println("XOR truth table");
        System.out.println((!false && false) || (false && !false));
        System.out.println((!false && true) || (false && !true));
        System.out.println((!true && false) || (true && !false));
        System.out.println((!true && true) || (true && !true));

        System.out.println("Length of args: " + args.length);

        int x;
        x = 65;
        System.out.println(x);

        double y = Math.PI;
        System.out.println(y);

        java.io.PrintStream out = System.out;
        System.out.println(out);
        System.out.println((char)x); // A

        System.out.println((char)++x); // B
        System.out.println((char)x++); // B
        System.out.println((char)x); // C

        x = 65;
        System.out.println((char)(x <<= 1) - 10);
        System.out.println(x);

        System.out.println(String.class);

        System.out.println("Blah" instanceof String);
        System.out.println("Blah" instanceof Object);
        System.out.println("Blah" instanceof CharSequence);
        System.out.println("Blah" instanceof java.io.PrintStream);

        int[] arr = new int[10];
        arr[0] = 5;
        arr[1] = 2;
        System.out.println(arr[0]);
        System.out.println(arr[9]);
    }
}
