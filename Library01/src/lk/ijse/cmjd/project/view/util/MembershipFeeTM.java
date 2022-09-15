package lk.ijse.cmjd.project.view.util;

import java.time.LocalDate;

public class MembershipFeeTM {
    private String memberId;
    private double yearlyAmount;
    private LocalDate dateOfPayment;

    public MembershipFeeTM() {
    }

    public MembershipFeeTM(String memberId, double yearlyAmount, LocalDate dateOfPayment) {
        this.memberId = memberId;
        this.yearlyAmount = yearlyAmount;
        this.dateOfPayment = dateOfPayment;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public double getYearlyAmount() {
        return yearlyAmount;
    }

    public void setYearlyAmount(double yearlyAmount) {
        this.yearlyAmount = yearlyAmount;
    }

    public LocalDate getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(LocalDate dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    @Override
    public String toString() {
        return "MembershipFeeTM{" +
                "memberId='" + memberId + '\'' +
                ", yearlyAmount=" + yearlyAmount +
                ", dateOfPayment=" + dateOfPayment +
                '}';
    }
}
