package lk.ijse.cmjd.project.entity;

import java.sql.Date;

public class Borrow {
    private String memberId;
    private String bookId;
    private Date issueDate;
    private Date dueDate;

    public Borrow() {
    }

    public Borrow(String memberId, String bookId, Date issueDate, Date dueDate) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.issueDate = issueDate;
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

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "memberId='" + memberId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                '}';
    }
}
