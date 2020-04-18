class ClassStructureGroup1 extends javax.swing.JDialog {

    private String first, last;
    double x = 5, y, z = 19.8;
    int[] a1 = { 4, 8 }, a2 = new int[100], a3, a4 = a1;

    void f(final double z, int x, final String[] s) {}

    public ClassStructureGroup1(Window owner, String title, boolean modal) {
        super(owner, title, modal);
    }

    public ClassStructureGroup1() {
        this(null, "Window title", true);
    }

}