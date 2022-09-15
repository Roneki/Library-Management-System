package lk.ijse.cmjd.project.entity;

import java.sql.Date;

public class MembershipFee {
    private String memberId;
    private double yearlyAmount;
    private Date dateOfPayment;

    public MembershipFee() {
    }

    public MembershipFee(String memberId, double yearlyAmount, Date dateOfPayment) {
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

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    @Override
    public String toString() {
        return "MembershipFee{" +
                "memberId='" + memberId + '\'' +
                ", yearlyAmount=" + yearlyAmount +
                ", dateOfPayment=" + dateOfPayment +
                '}';
    }
}
