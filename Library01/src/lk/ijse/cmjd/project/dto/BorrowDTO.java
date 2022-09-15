package lk.ijse.cmjd.project.dto;

import java.time.LocalDate;

public class BorrowDTO {
    private String memberId;
    private String bookId;
    private LocalDate issueDate;
    private LocalDate dueDate;

    public BorrowDTO() {
    }

    public BorrowDTO(String memberId, String bookId, LocalDate issueDate, LocalDate dueDate) {
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

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "BorrowDTO{" +
                "memberId='" + memberId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                '}';
    }
}
