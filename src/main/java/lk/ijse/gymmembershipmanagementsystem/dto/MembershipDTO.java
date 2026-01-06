package lk.ijse.gymmembershipmanagementsystem.dto;

import java.time.LocalDate;

public class MembershipDTO extends MemberDTO {
    private int membershipId;
    private int memberId;
    private String membershipType;
    private LocalDate issuedDate;
    private LocalDate expiryDate;
    private String memberName;

    public MembershipDTO() {
    }

    public MembershipDTO(int memberId, String membershipType, LocalDate issuedDate, LocalDate expiryDate) {
        this.memberId = memberId;
        this.membershipType = membershipType;
        this.issuedDate = issuedDate;
        this.expiryDate = expiryDate;
    }

    public MembershipDTO(int membershipId, int memberId, String membershipType, LocalDate issuedDate, LocalDate expiryDate) {
        this.membershipId = membershipId;
        this.memberId = memberId;
        this.membershipType = membershipType;
        this.issuedDate = issuedDate;
        this.expiryDate = expiryDate;
    }

    public MembershipDTO(int membershipId, int memberId, String membershipType, LocalDate issuedDate, LocalDate expiryDate, String memberName) {
        this.membershipId = membershipId;
        this.memberId = memberId;
        this.membershipType = membershipType;
        this.issuedDate = issuedDate;
        this.expiryDate = expiryDate;
        this.memberName = memberName;
    }

    public int getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @Override
    public String toString() {
        return "MembershipDTO{" + "membershipId=" + membershipId + ", memberId=" + memberId + ", membershipType=" + membershipType + ", issuedDate=" + issuedDate + ", expiryDate=" + expiryDate + '}';
    }
}
