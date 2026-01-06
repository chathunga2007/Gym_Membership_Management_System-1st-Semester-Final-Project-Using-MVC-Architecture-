package lk.ijse.gymmembershipmanagementsystem.dto;

public class EquipmentDTO {
    private int equipmenstId;
    private String name;
    private int qty;
    private String availability;

    public EquipmentDTO() {
    }

    public EquipmentDTO(String name, int qty, String availability) {
        this.name = name;
        this.qty = qty;
        this.availability = availability;
    }

    public EquipmentDTO(int equipmenstId, String name, int qty, String availability) {
        this.equipmenstId = equipmenstId;
        this.name = name;
        this.qty = qty;
        this.availability = availability;
    }

    public int getEquipmentsId() {
        return equipmenstId;
    }

    public void setEquipmentsId(int equipmenstId) {
        this.equipmenstId = equipmenstId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "EquipmentDTO{" + "equipmentsId=" + equipmenstId + ", name=" + name + ", qty=" + qty + ", availability=" + availability + '}';
    }
}
