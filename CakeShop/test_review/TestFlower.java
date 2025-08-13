public class TestFlower {
    public static void test() {
        // Dùng constructor mặc định
        Flower f1 = new Flower();
        f1.display();

        // Dùng constructor 1 tham số (int)
        Flower f2 = new Flower(10);
        f2.display();

        // Dùng constructor 1 tham số (String)
        Flower f3 = new Flower("Rose");
        f3.display();

        // Dùng constructor 2 tham số
        Flower f4 = new Flower("Tulip", 12);
        f4.display();
    }
}
