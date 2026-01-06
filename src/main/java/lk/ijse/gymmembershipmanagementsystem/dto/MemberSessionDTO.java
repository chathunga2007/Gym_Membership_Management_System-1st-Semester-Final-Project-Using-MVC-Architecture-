package lk.ijse.gymmembershipmanagementsystem.dto;

import java.time.LocalDateTime;

public class MemberSessionDTO {
    private int memberId;
    private int sessionId;
    private LocalDateTime joinedAt;

    public MemberSessionDTO() {
    }

    public MemberSessionDTO(int memberId, int sessionId, LocalDateTime joinedAt) {
        this.memberId = memberId;
        this.sessionId = sessionId;
        this.joinedAt = joinedAt;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    @Override
    public String toString() {
        return "MemberSessionDTO{" + "memberId=" + memberId + ", sessionId=" + sessionId + ", joinedAt=" + joinedAt + '}';
    }
}
