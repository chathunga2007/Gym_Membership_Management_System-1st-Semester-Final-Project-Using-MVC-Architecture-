package lk.ijse.gymmembershipmanagementsystem.dto;

import java.time.LocalDate;

public class SessionDTO {
    private int sessionId;
    private int slotId;
    private int trainerId;
    private String sessionType;
    private int duration;
    private int equipmentId;

    private String trainerName;
    private LocalDate slotDate;

    public SessionDTO() {
    }

    public SessionDTO(int slotId, int trainerId, String sessionType, int duration) {
        this.slotId = slotId;
        this.trainerId = trainerId;
        this.sessionType = sessionType;
        this.duration = duration;
    }

    public SessionDTO(int sessionId, int equipmentId, int duration, String sessionType, int trainerId, int slotId) {
        this.sessionId = sessionId;
        this.equipmentId = equipmentId;
        this.duration = duration;
        this.sessionType = sessionType;
        this.trainerId = trainerId;
        this.slotId = slotId;
    }

    public SessionDTO(int slotId, int trainerId, String sessionType, int equipmentId, int duration) {
        this.slotId = slotId;
        this.trainerId = trainerId;
        this.sessionType = sessionType;
        this.equipmentId = equipmentId;
        this.duration = duration;
    }

    public SessionDTO(int sessionId, int slotId, int trainerId, String sessionType, int duration) {
        this.sessionId = sessionId;
        this.slotId = slotId;
        this.trainerId = trainerId;
        this.sessionType = sessionType;
        this.duration = duration;
    }

    public SessionDTO(int sessionId, String sessionType, int duration,
                      int trainerId, String trainerName,
                      int slotId, LocalDate slotDate) {
        this.sessionId = sessionId;
        this.sessionType = sessionType;
        this.duration = duration;
        this.trainerId = trainerId;
        this.trainerName = trainerName;
        this.slotId = slotId;
        this.slotDate = slotDate;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public LocalDate getSlotDate() {
        return slotDate;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "SessionDTO{" + "sessionId=" + sessionId + ", slotId=" + slotId + ", trainerId=" + trainerId + ", sessionType=" + sessionType + ", duration=" + duration + '}';
    }
}
