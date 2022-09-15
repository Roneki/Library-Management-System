package lk.ijse.cmjd.project.dto;

public class FinePaymentDTO {
    private int fineNo;
    private String bookId;
    private String memberId;
    private int noOfDaysPassed;
    private  double amount;

    public FinePaymentDTO() {
    }

    public FinePaymentDTO(int fineNo, String bookId, String memberId, int noOfDaysPassed, double amount) {
        this.fineNo = fineNo;
        this.bookId = bookId;
        this.memberId = memberId;
        this.noOfDaysPassed = noOfDaysPassed;
        this.amount = amount;
    }

    public int getFineNo() {
        return fineNo;
    }

    public void setFineNo(int fineNo) {
        this.fineNo = fineNo;
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

    public int getNoOfDaysPassed() {
        return noOfDaysPassed;
    }

    public void setNoOfDaysPassed(int noOfDaysPassed) {
        this.noOfDaysPassed = noOfDaysPassed;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "FinePaymentDTO{" +
                "fineNo=" + fineNo +
                ", bookId='" + bookId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", noOfDaysPassed=" + noOfDaysPassed +
                ", amount=" + amount +
                '}';
    }
}
