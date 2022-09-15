package lk.ijse.cmjd.project.entity;

import java.sql.Date;

public class ReturnPK {
    private String bookId;
    private String memberId;
    private Date dueDate;

    public ReturnPK() {
    }

    public ReturnPK(String bookId, String memberId, Date dueDate) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.dueDate = dueDate;
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "ReturnPK{" +
                "bookId='" + bookId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }
}
