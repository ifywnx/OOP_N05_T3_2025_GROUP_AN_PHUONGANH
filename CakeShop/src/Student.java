public class Student implements Comparable<Student> {
    private String name;
    private float gpa;

    public Student(String name, float gpa) {
        this.name = name;
        this.gpa = gpa;
    }

    public Student() {}

    public String getName() {
        return name;
    }

    public float getGpa() {
        return gpa;
    }

    @Override
    public int compareTo(Student o) {
        // So sánh GPA giảm dần
        if (this.gpa < o.gpa) return 1;
        else if (this.gpa > o.gpa) return -1;
        else {
            // Nếu GPA bằng thì so sánh theo tên tăng dần (tùy chọn)
            return this.name.compareTo(o.name);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // So sánh cùng tham chiếu
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;  // Kiểm tra kiểu đối tượng
        Student other = (Student) o;
        // So sánh cả name và gpa
        return Float.compare(this.gpa, other.gpa) == 0 &&
               (this.name == null ? other.name == null : this.name.equals(other.name));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (name == null ? 0 : name.hashCode());
        result = 31 * result + Float.hashCode(gpa);
        return result;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', gpa=" + gpa + "}";
    }
}
