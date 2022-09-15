package lk.ijse.cmjd.project.view.util;

import java.time.LocalDate;

public class RenewTM {
    private String memberId;
    private  String bookId;
    private LocalDate date;
    private LocalDate dueDate;

    public RenewTM() {
    }

    public RenewTM(String memberId, String bookId, LocalDate date, LocalDate dueDate) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "RenewTM{" +
                "memberId='" + memberId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", date=" + date +
                ", dueDate=" + dueDate +
                '}';
    }
}
