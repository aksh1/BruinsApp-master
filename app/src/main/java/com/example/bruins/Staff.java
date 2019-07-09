package com.example.bruins;

public class Staff {
    private String name;
    private String email;

    public Staff() {
    }

    public Staff(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}