package lk.ijse.gymmembershipmanagementsystem.dto;

import java.time.LocalDateTime;

public class PrivateSessionDTO {
    private int privateSessionId;
    private int memberId;
    private String timeIn;
    private String timeOut;
    private double extraCharges;
    private String memberName;

    public PrivateSessionDTO() {
    }

    public PrivateSessionDTO(int privateSessionId, int memberId, String timeIn, String timeOut, double extraCharges) {
        this.privateSessionId = privateSessionId;
        this.memberId = memberId;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.extraCharges = extraCharges;
    }

    public PrivateSessionDTO(int privateSessionId, int memberId, String timeIn, String timeOut, double extraCharges, String memberName) {
        this.privateSessionId = privateSessionId;
        this.memberId = memberId;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.extraCharges = extraCharges;
        this.memberName = memberName;
    }

    public PrivateSessionDTO(int memberId, String timeIn, String timeOut, double extraCharges) {
        this.memberId = memberId;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.extraCharges = extraCharges;
    }

    public int getPrivateSessionId() {
        return privateSessionId;
    }

    public void setPrivateSessionId(int privateSessionId) {
        this.privateSessionId = privateSessionId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public double getExtraCharges() {
        return extraCharges;
    }

    public void setExtraCharges(double extraCharges) {
        this.extraCharges = extraCharges;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
