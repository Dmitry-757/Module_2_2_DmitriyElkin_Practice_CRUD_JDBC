package com.Dmitry_Elkin.PracticeTaskCRUD.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Skill {
    private long id;
    private String name;
    private Status status;

    public Skill(long id, String name) {
        this.id = id;
        this.name = name;
        status = Status.ACTIVE;
    }

    public Skill(String name) {
        this.name = name;
        status = Status.ACTIVE;

    }
    public Skill(long id, String name, int statusId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return id == skill.id && name.equals(skill.name) && status == skill.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status);
    }


    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", Name='" + name + '\'' +
                ", status=" + status +
                '}';
    }

    public Status getStatus() {
        return status;
    }
}
