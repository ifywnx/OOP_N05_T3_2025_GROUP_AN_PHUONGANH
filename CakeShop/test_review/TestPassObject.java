public class TestPassObject {
    public static void main(String[] args) {
        Number n = new Number();
        n.i =14;
        PassObject.f(n);
        System.out.println(n.i);
// what is n.i now? 15 or 14
    }
}