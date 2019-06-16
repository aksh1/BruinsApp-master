package com.example.bruins;

public class Staff {

    private String mName;
    private String mEmail;


    public Staff() {
        //empty constructor needed
    }

    public Staff(String name, String email) {
        mName = name;
        mEmail = email;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = mName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

}
