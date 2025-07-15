import java.util.List;
public class Library {
    private List<Book> books;

    public Library(List<Book> b) {
        books = b;
    }
    public List<Book> getBooks() {
        return books;
    }
}
