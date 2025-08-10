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
        // Sắp xếp GPA giảm dần
        if (this.gpa < o.gpa) return 1;
        else if (this.gpa > o.gpa) return -1;
        else return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student other = (Student) obj;
        return Float.compare(other.gpa, gpa) == 0 && name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + Float.hashCode(gpa);
    }

    @Override
    public String toString() {
        return name + " - " + gpa;
    }
}
