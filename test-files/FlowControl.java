package test;

import java.util.*;

public class FlowControl {

    public static void main(String[] args) {
        if (args.length > 0)
            System.out.println(args.length + " arguments");

        if (args.length > 0) {
            System.out.println("First argument: " + args[0]);
        } else
            System.out.println("No arguments!");

        if (args.length > 0) {
            if (args.length > 0)
                System.out.println("Nested if OK (multiple arguments)");
            else
                System.out.println("Nested if broken (multiple arguments)");
        } else {
            if (!(args.length > 0)) {
                System.out.println("Nested if OK (no arguments)");
            } else System.out.println("Nested if broken (no arguments)");
        }

        int x = 10;
        while (x > 0) {
            System.out.println(x + "...");
            --x;
        }
        System.out.println("Blastoff!");

        int y = 10;
        do {
            System.out.println(y + "...");
            --y;
        } while (y > 0);
        System.out.println("Blastoff!");

        for (int count = 10; count > 0; --count)
            System.out.println(count + "...");
        System.out.println("Blastoff!");

        int n = 27;
        while (n != 1) {
            System.out.println(n);
            if (n % 2 == 0)
                n >>= 1;
            else
                n += (n << 1) + 1;
        }
        System.out.println(n);

        n = 1000;
        boolean[] flags = new boolean[n + 1];
        for (int i = 2; i < flags.length; ++i)
            flags[i] = true;
        for (int i = 2; i * i <= n; ++i) {
            for (int j = i << 1; j <= n; j += i)
                flags[j] = false;
        }

        for (int i = 0; i < flags.length; ++i) {
            if (flags[i])
                System.out.print(i + " ");
        }
        System.out.println();

        Random r = new Random(0);
        double rand = r.nextDouble();
        double[] data = new double[20000];
        for (int i = 0; i < data.length; ++i)
            data[i] = r.nextDouble();
        //printArray(data);
        //quicksort(data);
        sort(data);
        //printArray(data);
    }

    private static void sort(double[] numbers) {
        double temp;
        for (int i = 0; i < numbers.length; ++i) {
            for (int j = 0; j + 1 < numbers.length; ++j) {
                if (numbers[j] > numbers[j + 1]) {
                    temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }
    }

    private static void printArray(double[] arr) {
        for (int i = 0; i + 1 < arr.length; ++i)
            System.out.print(arr[i] + ", ");
        if (arr.length > 0)
            System.out.println(arr[arr.length - 1]);
    }

    private static void quicksort(double[] numbers) {
        quicksort(numbers, 0, numbers.length);
    }

    private static void quicksort(double[] numbers, int a, int b) {
        if (a + 1 >= b)
            return;

        int piv = a;
        for (int i = a + 1; i < b; ++i) {
            if (numbers[i] < numbers[piv]) {
                swap(numbers, piv + 1, i);
                swap(numbers, piv, piv + 1);
                ++piv;
            }
        }

        quicksort(numbers, a, piv);
        quicksort(numbers, piv + 1, b);
    }

    private static void swap(double[] arr, int i, int j) {
        double temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}