package lk.ijse.cmjd.project.entity;

import java.sql.Date;


public class Member {
    private String memberId;
    private String name;
    private  String address;
    private String memberType;
    private Date membershipDate;

    public Member() {
    }

    public Member(String memberId, String name, String address, String memberType, Date membershipDate) {
        this.memberId = memberId;
        this.name = name;
        this.address = address;
        this.memberType = memberType;
        this.membershipDate = membershipDate;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public Date getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(Date membershipDate) {
        this.membershipDate = membershipDate;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId='" + memberId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", memberType='" + memberType + '\'' +
                ", membershipDate=" + membershipDate +
                '}';
    }
}
