
public class App {
    public static void main(String[] args) throws Exception {

        TestKhachhang  tkh = new TestKhachhang();
        tkh.test();

        TestNumber tn = new TestNumber();
        tn.test();

        TestObject to = new TestObject();
        to.test();

        TestBreakAndContinue tbac = new TestBreakAndContinue();
        tbac.test();
    }
}
