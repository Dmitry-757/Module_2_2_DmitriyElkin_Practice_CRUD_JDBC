package com.Dmitry_Elkin.PracticeTaskCRUD.model;

public enum Status {
    ACTIVE(1),
    DELETED(0);

    private int status;

    Status(int i) {
        this.status = i;
    }

    public int getStatus() {
        return status;
    }
}
