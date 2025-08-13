public class TestNNCollection {
    public static void test() {
        NNCollection db = new NNCollection();

        db.insert(new NameNumber("An", "0901234567"));
        db.insert(new NameNumber("Binh", "0911222333"));
        db.insert(new NameNumber("Chi", "0987111222"));

        System.out.println(db.findNumber("Binh"));  // In ra: 0911222333
        System.out.println(db.findNumber("Duc"));   // In ra: Name not found
    }
}
