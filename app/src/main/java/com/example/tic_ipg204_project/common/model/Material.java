package com.example.tic_ipg204_project.common.model;

import androidx.annotation.NonNull;

public class Material {
    private int materialId;
    private String materialName;
    private String description;
    private boolean isService;

    public Material(int materialId, String materialName, String description, boolean isService) {
        this.materialId = materialId;
        this.materialName = materialName;
        this.description = description;
        this.isService = isService;
    }

    public String getDescription() {
        return description;
    }

    public boolean isService() {
        return isService;
    }

    public int getMaterialId() {
        return materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    @NonNull
    @Override
    public String toString() {
        return this.materialName; //whatever the value which you want to show in spinner list.
    }
}
