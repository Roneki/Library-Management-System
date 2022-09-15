package lk.ijse.cmjd.project.entity;

import java.sql.Date;

public class Return {
    private String memberId;
    private String bookId;
    private Date dueDate;
    private Date returnDate;
    private boolean fineNeeded;

    public Return() {
    }

    public Return(String memberId, String bookId, Date dueDate, Date returnDate, boolean fineNeeded) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fineNeeded = fineNeeded;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isFineNeeded() {
        return fineNeeded;
    }

    public void setFineNeeded(boolean fineNeeded) {
        this.fineNeeded = fineNeeded;
    }

    @Override
    public String toString() {
        return "Return{" +
                "memberId='" + memberId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", fineNeeded=" + fineNeeded +
                '}';
    }
}
