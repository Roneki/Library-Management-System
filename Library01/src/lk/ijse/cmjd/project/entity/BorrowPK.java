package lk.ijse.cmjd.project.entity;

import java.sql.Date;

public class BorrowPK {
    private String bookId;
    private String memberId;
    private Date issueDate;

    public BorrowPK() {
    }

    public BorrowPK(String bookId, String memberId, Date issueDate) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.issueDate = issueDate;
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

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    @Override
    public String toString() {
        return "BorrowPK{" +
                "bookId='" + bookId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", issueDate=" + issueDate +
                '}';
    }
}
