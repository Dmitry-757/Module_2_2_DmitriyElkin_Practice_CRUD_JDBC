package com.Dmitry_Elkin.PracticeTaskCRUD.model;

public enum Status {
    ACTIVE(1),
    DELETED(0);

    private int status;

    Status(int i) {
        status = i;
    }

    public int getStatusValue() {
        return status;
    }

    public static Status getStatus(int i){
        return switch (i){
            case 1-> Status.ACTIVE;
            default -> Status.DELETED;
        };
    }

}
