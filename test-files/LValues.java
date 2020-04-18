package test;

public class LValues {

    private int age = 39;
    private char[] initials = new char[3];
    private static final double Z = Math.PI;

    /*
    public LValues() {
    }
     */

    private void testLocals() {
        System.out.println("testLocals()");
        ++Z;
        System.out.println("Incremented Z");
        String name = "Marjorie";
        int[] nums = new int[5];
        int z = 40;

        System.out.println(name);
        name = "Gretchen";
        System.out.println(name);
        name += " Wheeler";
        System.out.println(name);

        System.out.println(nums[0]);
        nums[0] = 2;
        System.out.println(nums[0]);
        nums[1] = 3 * nums[0] - 1; // 5
        System.out.println(nums[1]);
        nums[2] = 11 * nums[1]; // 55
        System.out.println(++nums[2]); // 56
        System.out.println(nums[2]++); // 56
        System.out.println(nums[2]); // 57

        System.out.println(z);
        ++z;
        System.out.println(z);
    }

    private void testParams(String name, double x, char[] letters) {
        this.Z++;
        System.out.println("testParams(" + name + ", " + x + ", " + letters + ")");
        name += " Nightmare";
        System.out.println(name);

        x /= 4;
        System.out.println(x); // 6.5
        System.out.println(--x); // 5.5

        letters[0] = 'U';
        letters[1] = 't';
        letters[2] = 'a';
        letters[3] = 'h';
        System.out.println(new String(letters));
    }

    private void testArrays() {

    }

    private void testFields() {
        System.out.println("testFields()");

        //this.age = 39;
        System.out.println(age); // 39
        System.out.println(++age); // 40
        System.out.println(this.age++); // 40
        System.out.println(this.age); // 41

        //this.initials = new char[3];
        initials[0] = 'K';
        initials[1] = 'D';
        this.initials[2] = 'L';
        System.out.println(new String(initials));

        ++initials[0];
        this.initials[1]++;
        this.initials[2] = (char)(initials[2] - 3);
        System.out.println(new String(this.initials));
    }

    private void testStaticFields() {

    }

    public static void main(String[] args) {
        System.out.println("LValues.Z = " + LValues.Z);
        LValues lvalues = new LValues();
        lvalues.testLocals();
        lvalues.testParams("Foursquare", 26.0, new char[4]);
        lvalues.testArrays();
        lvalues.testFields();
        lvalues.testStaticFields();
        System.out.println("LValues.Z = " + LValues.Z);
    }
}