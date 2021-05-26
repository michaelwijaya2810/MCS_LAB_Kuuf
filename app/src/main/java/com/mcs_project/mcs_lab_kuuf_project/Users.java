package com.mcs_project.mcs_lab_kuuf_project;

public class Users {
//    public Users(int id, String username, String password, String phone, String gender, long wallet, String dateOfBirth) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.phone = phone;
//        this.gender = gender;
//        this.wallet = wallet;
//        this.dateOfBirth = dateOfBirth;
//    }

    int id;
    String username;
    String password;
    String phone;
    String gender;
    long wallet;
    String dateOfBirth;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getWallet() {
        return wallet;
    }

    public void setWallet(long wallet) {
        this.wallet = wallet;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
