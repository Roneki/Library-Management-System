package lk.ijse.cmjd.project.view.util;

public class BookTM {
    private String bookId;
    private String Title;
    private String subject;
    private String author;
    private double price;
    private int fullQty;
    private  int qtyOnHand;
    private String publisherId;

    public BookTM() {
    }

    public BookTM(String bookId, String title, String subject, String author, double price, int fullQty, int qtyOnHand, String publisherId) {
        this.bookId = bookId;
        Title = title;
        this.subject = subject;
        this.author = author;
        this.price = price;
        this.fullQty = fullQty;
        this.qtyOnHand = qtyOnHand;
        this.publisherId = publisherId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getFullQty() {
        return fullQty;
    }

    public void setFullQty(int fullQty) {
        this.fullQty = fullQty;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    @Override
    public String toString() {
        return "BookTM{" +
                "bookId='" + bookId + '\'' +
                ", Title='" + Title + '\'' +
                ", subject='" + subject + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", fullQty=" + fullQty +
                ", qtyOnHand=" + qtyOnHand +
                ", publisherId='" + publisherId + '\'' +
                '}';
    }
}
