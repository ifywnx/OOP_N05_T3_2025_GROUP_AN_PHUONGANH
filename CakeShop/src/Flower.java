public class Flower {
    int petalCount = 0;
    String s = "null";

    // Constructor 1: chỉ truyền số cánh
    public Flower(int petals) {
        petalCount = petals;
    }

    // Constructor 2: chỉ truyền chuỗi
    public Flower(String ss) {
        s = ss;
    }

    // Constructor 3: truyền cả chuỗi và số cánh
    public Flower(String s, int petals) {
        this(petals);      // Gọi constructor (int)
        this.s = s;        // Gán chuỗi vào biến s của đối tượng
    }

    // Constructor 4: mặc định
    public Flower() {
        this("hi", 47);    // Gọi constructor (String, int)
    }

    // Hiển thị thông tin
    public void display() {
        System.out.println("s = " + s + ", petalCount = " + petalCount);
    }
}
