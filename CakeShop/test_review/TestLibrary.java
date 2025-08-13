import java.util.ArrayList;
import java.util.List;

public class TestLibrary {
    public static void test() {
        Book b1 = new Book("1", "test1");
        Book b2 = new Book("2", "test2");

        List<Book> list = new ArrayList<>();
        list.add(b1);
        list.add(b2);

        Library lib = new Library(list);

        // In danh sách sách trong thư viện
        for (Book b : lib.getBooks()) {
            System.out.println(b.getId() + " - " + b.getTitle());
        }
    }
}
