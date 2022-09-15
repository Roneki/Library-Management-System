package lk.ijse.cmjd.project.entity;

public class LostPayment {
    private  int number;
    private String bookId;
    private String memberId;
    private double bookPrice;
    private double payment;

    public LostPayment() {
    }

    public LostPayment(int number, String bookId, String memberId, double bookPrice, double payment) {
        this.number = number;
        this.bookId = bookId;
        this.memberId = memberId;
        this.bookPrice = bookPrice;
        this.payment = payment;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "LostPayment{" +
                "number=" + number +
                ", bookId='" + bookId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", bookPrice=" + bookPrice +
                ", payment=" + payment +
                '}';
    }
}
