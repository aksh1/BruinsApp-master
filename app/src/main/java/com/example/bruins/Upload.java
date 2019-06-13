package com.example.bruins;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String mUsername;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String name, String imageUrl, String username) {
        if (name.trim().equals("")) {
            name = null;
        }

        mName = name;
        mImageUrl = imageUrl;
        mUsername = username;
    }



    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
