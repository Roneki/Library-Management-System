package lk.ijse.cmjd.project.entity;

import java.sql.Date;

public class RenewPK {
    private  String  memberId;
    private  String bookId;
    private Date date;

    public RenewPK() {
    }

    public RenewPK(String memberId, String bookId, Date date) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.date = date;
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

    @Override
    public String toString() {
        return "RenewPK{" +
                "memberId='" + memberId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", date=" + date +
                '}';
    }
}
