package lk.ijse.gymmembershipmanagementsystem.dto;

public class TrainerDTO {
    private int trainerId;
    private String name;
    private int age;
    private String nic;
    private String contact;
    private String email;

    public TrainerDTO() {
    }
    
    public TrainerDTO(int trainerId, String name) {
        this.trainerId = trainerId;
        this.name = name;
    }

    public TrainerDTO(String name, int age, String nic, String contact, String email) {
        this.name = name;
        this.age = age;
        this.nic = nic;
        this.contact = contact;
        this.email = email;
    }

    public TrainerDTO(int trainerId, String name, int age, String nic, String contact, String email) {
        this.trainerId = trainerId;
        this.name = name;
        this.age = age;
        this.nic = nic;
        this.contact = contact;
        this.email = email;
    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return trainerId + " - " + name;
    }
}
