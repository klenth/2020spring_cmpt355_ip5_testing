class StatementsGroup1 {

    void f() {
        final int x;
        final double z = Math.PI;

        String first, last;
        double x = 5, y, z = 19.8;
        int[] a1 = { 4, 8 }, a2 = new int[100], a3, a4 = a1;

        this.f();
        super.f();
        this.doSomethingWithArguments(1, 2, "skip a few", 99, 100);

        for (var x : a1) {
            for (var y : a2) {
                for (var z : a4)
                    System.out.printf("%d, %d, %d\n", x, y, z);
            }
        }

        switch (x) {}

        switch (2 * x - y) {
            case 0:
            case 1:
            case 2:
                System.out.println("0/1/2");
            case 3:
                System.out.println("0/1/2/3");
                break;
            default:
                System.out.println("Something else");
        }
    }
}