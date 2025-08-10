import java.util.Set;
import java.util.TreeSet;

public class TestStudent {
    public static void test() {
        Student s1 = new Student("An", 3.5f);
        Student s2 = new Student("Bình", 3.5f);
        Student s3 = new Student("Cường", 3.9f);
        Student s4 = new Student("Dũng", 2.8f);
        Student s5 = new Student("An", 3.5f); // giống s1

        // Kiểm thử equals()
        System.out.println("s1 equals s2? " + s1.equals(s2)); // false (khác tên)
        System.out.println("s1 equals s5? " + s1.equals(s5)); // true (trùng tên và gpa)

        // Kiểm thử compareTo()
        System.out.println("So sánh s1 và s3: " + s1.compareTo(s3)); // >0
        System.out.println("So sánh s3 và s1: " + s3.compareTo(s1)); // <0
        System.out.println("So sánh s1 và s2: " + s1.compareTo(s2)); // =0

        // Sử dụng TreeSet
        Set<Student> students = new TreeSet<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        students.add(s5); // trùng với s1 nên không thêm

        System.out.println("\nDanh sách sinh viên (sắp xếp theo GPA giảm dần, không trùng GPA):");
        for (Student s : students) {
            System.out.println(s);
        }
    }
}
