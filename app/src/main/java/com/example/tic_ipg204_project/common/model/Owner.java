package com.example.tic_ipg204_project.common.model;

public class Owner {
    private int ownerId;
    private String ownerName;
    private String description;

    public Owner(int ownerId, String ownerName, String description) {
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
