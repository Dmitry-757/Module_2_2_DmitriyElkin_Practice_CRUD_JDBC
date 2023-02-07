package com.Dmitry_Elkin.PracticeTaskCRUD.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Specialty {

    private long id;
    private String name;
    private Status status;

    public Specialty(long id, String name) {
        this.id = id;
        this.name = name;
        status = Status.ACTIVE;
    }

    public Specialty(String name) {
        this.name = name;
        status = Status.ACTIVE;
    }

    public Specialty(long id, String name, int statusId) {
        this.id = id;
        this.name = name;
        status = Status.getStatusById(statusId);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDeleted() {
        status = Status.DELETED;
    }

    public void setUnDeleted() {
        status = Status.ACTIVE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specialty specialty = (Specialty) o;
        return id == specialty.id && name.equals(specialty.name) && status == specialty.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status);
    }


    @Override
    public String toString() {
        return "Specialty{" +
                "id=" + id +
                ", Name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
