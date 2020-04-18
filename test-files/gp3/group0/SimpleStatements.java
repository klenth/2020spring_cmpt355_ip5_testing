public class SimpleStatements {

    void f() {
        // The simplest statement of all
        ;

        // An empty block
        { }

        // A non-empty block
        {
            ;
        }

        // A syntactically-correct crying face
        { ; ; }

        // Variable declarations
        int x;
        int y = 25;
        String s;
        String[] names = new String[y];

        // Assignment statements
        x = 19;
        y &= 14;
        x.y.z = 100;
        this.x = x;
        ++y;
        z--;

        // Method calls
        System.out.println("Hi");
        System.exit(0);
        f();
        g(5, "blah", x);

        // Return statements
        return;
        return 2 * x - 4;

        // Instantiation statements
        new Scanner(System.in);
        new SimpleStatements();

        // Break and continue
        break;
        continue;
    }
}