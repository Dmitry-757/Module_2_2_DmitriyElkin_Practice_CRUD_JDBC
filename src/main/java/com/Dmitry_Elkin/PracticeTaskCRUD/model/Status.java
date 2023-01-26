package com.Dmitry_Elkin.PracticeTaskCRUD.model;

public enum Status {
    ACTIVE(1),
    DELETED(0);

    private int id;

    Status(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Status getStatusById(int i){
        return switch (i){
            case 1 -> Status.ACTIVE;
            case 0 -> Status.DELETED;
            default -> null;
        };
    }

}
