package com.example.tic_ipg204_project.common.model;

public class OutLay {
    private int id;
    private int ownerId;
    private int materialId;
    private double price;
    private String date;
    private String description;

    public OutLay(int id, int ownerId, int materialId, double price, String date, String description) {
        this.id = id;
        this.ownerId = ownerId;
        this.materialId = materialId;
        this.price = price;
        this.date = date;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}
