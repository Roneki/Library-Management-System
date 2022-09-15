package lk.ijse.cmjd.project.dto;

import java.time.LocalDate;

public class ReturnDTO {
    private String memberId;
    private String bookId;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean fineNeeded;

    public ReturnDTO() {
    }

    public ReturnDTO(String memberId, String bookId, LocalDate dueDate, LocalDate returnDate, boolean fineNeeded) {
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
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
        return "ReturnDTO{" +
                "memberId='" + memberId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", fineNeeded=" + fineNeeded +
                '}';
    }
}
