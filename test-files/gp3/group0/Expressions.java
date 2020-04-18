class Expressions {

    public static void main(String[] args) {
        int i;
        long l;
        float f;
        double d;
        boolean b;
        char c;
        String s;
        Object o;
        Badger humphrey = new Badger();
        double[] vals = new double[20];

        // Literals
        i = 3929;
        l = 29323092302L;
        f = 23f;
        d = 23.;
        b = false;
        c = '\n';
        s = "This is a string. Exciting, isn't it‽";
        o = null;

        // Variable, field, subscript reference
        l = i;
        humphrey.color = "black";
        humphrey.birthdate.month = Month.APRIL;
        vals[0] = 25;
        vals[vals.length - 1] = 100;
        humphrey.favoriteColors[0] = "blue";
        humphrey.children[0][0].first = "Yvonne";

        // Increments and unary operators
        i = --i;
        l = l++;
        b = !b;
        d = -5;

        // Binary operators
        i = i + 5;
        i = i >>> 2;
        b = (x > 0) || isPrime(y);
        b = o instanceof taxa.animal.Marsupial;

        // Other stuff
        o = new String("Blah");
        i = (int)l;
        humphrey = (taxa.animal.mammalia.Badger)o;
        new Thing(this);
        x = (5);
        (x) = 5;

        // Compound expressions
        vals[-x - 1] = vals[f()] * (i << (int)Math.ceil(Math.sqrt(x)));
        o = List.of(f(), s, o).addAll("stuff") + "Hi" + Math.pow(x, 2.8f);
    }
}