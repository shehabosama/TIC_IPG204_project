package com.example.tic_ipg204_project.common.model;

public class UserVacationRequest {
    int numberOfDay;
    String startDate;
    String managerName;
    String reason;
    String name;
    int uId;

    public UserVacationRequest(int uId,int numberOfDay, String startDate, String managerName, String reason , String name) {
        this.numberOfDay = numberOfDay;
        this.startDate = startDate;
        this.managerName = managerName;
        this.reason = reason;
        this.name = name;
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public int getuId() {
        return uId;
    }

    public int getNumberOfDay() {
        return numberOfDay;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getManagerName() {
        return managerName;
    }

    public String getReason() {
        return reason;
    }
}
