package com.example.ssneddon.notetakingapp.entity;

public class Mentor {

    private int key;
    private String name;
    private String phone;
    private String email;

    public Mentor() {
    }

    public Mentor(int key, String name, String phone, String email) {
        this.key = key;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
