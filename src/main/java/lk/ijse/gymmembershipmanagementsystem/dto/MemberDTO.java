package lk.ijse.gymmembershipmanagementsystem.dto;

import java.time.LocalDate;

public class MemberDTO {
    
    private int memberID;
    private String name;
    private LocalDate dob;
    private int age;
    private String email;
    private String nic;
    private String contact;
    private LocalDate joinedDate;
    private String status;

    public MemberDTO() {
    }

    public MemberDTO( int memberID,String name) {
        this.name = name;
        this.memberID = memberID;
    }

    public MemberDTO(String name, LocalDate dob, int age, String email, String nic, String contact, LocalDate joinedDate, String status) {
        this.name = name;
        this.dob = dob;
        this.age = age;
        this.email = email;
        this.nic = nic;
        this.contact = contact;
        this.joinedDate = joinedDate;
        this.status = status;
    }

    public MemberDTO(int memberID, String name, LocalDate dob, int age, String email, String nic, String contact, LocalDate joinedDate, String status) {
        this.memberID = memberID;
        this.name = name;
        this.dob = dob;
        this.age = age;
        this.email = email;
        this.nic = nic;
        this.contact = contact;
        this.joinedDate = joinedDate;
        this.status = status;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(LocalDate joinedDate) {
        this.joinedDate = joinedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return memberID + " - " + name;
    }
}
