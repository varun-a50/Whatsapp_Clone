package com.example.whatsappclone.Models;

import java.util.ArrayList;

public class UserStatus {
    private String name, profileImage;
    private long lastUpdated;
    private ArrayList<Status> Statuses;

    public UserStatus() {
    }

    public UserStatus(String name, String profileImage, long lastUpdated, ArrayList<Status> statuses) {
        this.name = name;
        this.profileImage = profileImage;
        this.lastUpdated = lastUpdated;
        Statuses = statuses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public ArrayList<Status> getStatuses() {
        return Statuses;
    }

    public void setStatuses(ArrayList<Status> statuses) {
        Statuses = statuses;
    }
}
