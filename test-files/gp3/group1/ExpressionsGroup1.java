class ExpressionsGroup1 {

    public void f() {
        int x = 5;

        String s = (x > 20) ? "big" : "little";

        int monthNum = 2;
        int year = 2020;
        int daysInMonth = (monthNum == 1 || monthNum == 3 || monthNum == 5 || monthNum == 7 || monthNum == 8
                            || monthNum == 10 || monthNum == 12) ? 31
                : (monthNum == 2 || monthNum == 4 || monthNum == 6 || monthNum == 9 || monthNum == 1) ? 30
                : (year % 400 == 0 || year % 4 == 0 && year % 100 != 0) ? 29
                : 28;
    }
}