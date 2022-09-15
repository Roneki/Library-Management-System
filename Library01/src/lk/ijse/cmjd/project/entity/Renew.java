package lk.ijse.cmjd.project.entity;

import java.sql.Date;

public class Renew {
    private String memberId;
    private  String bookId;
    private Date date;
    private Date dueDate;

    public Renew() {

    }

    public Renew(String memberId, String bookId, Date date, Date dueDate) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.date = date;
        this.dueDate = dueDate;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Renew{" +
                "memberId='" + memberId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", date=" + date +
                ", dueDate=" + dueDate +
                '}';
    }
}
