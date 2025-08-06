import java.util.TreeSet;

public class StudentTest {
    public static void main(String[] args) {
        TreeSet<Student> studentSet = new TreeSet<>();

        studentSet.add(new Student("Alice", 3.5f));
        studentSet.add(new Student("Bob", 3.8f));
        studentSet.add(new Student("Charlie", 3.5f));
        studentSet.add(new Student("Diana", 4.0f));
        studentSet.add(new Student("Alice", 3.5f)); // Trùng với phần tử đầu (theo compareTo)

        // In ra các phần tử trong TreeSet, đã được sắp xếp và không trùng lặp
        for (Student s : studentSet) {
            System.out.println(s);
        }
    }
}

