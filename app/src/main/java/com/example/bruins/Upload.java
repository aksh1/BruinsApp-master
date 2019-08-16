package com.example.bruins;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String mUsername;
    private String mDate;
    private String mProfilePic;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String description, String link, String username, String date, String profile_pic) {
        mName = description;
        mImageUrl = link;
        mUsername = username;
        mDate = date;
        mProfilePic = profile_pic;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String username) {
        this.mUsername = username;
    }

    public String getName() {
        return mName;
    }

    public void setName(String description) {
        mName = description;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String link) {
        mImageUrl = link;
    }

    public String getProfilePicture() {
        return mProfilePic;
    }

    public void setProfilePicture(String profilePicture) {
        this.mProfilePic = profilePicture;
    }
}
