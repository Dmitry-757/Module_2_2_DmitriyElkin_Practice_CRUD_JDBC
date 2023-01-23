package com.Dmitry_Elkin.PracticeTaskCRUD.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

//implements BaseModelsMethsI - for my experiment by creating common repository
//meth getStaticLastId() is needed  for possibility to get lastId from instance of model-class
public class Specialty implements BaseModelsMethsI {

    private static volatile long lastId;
    static{
        if (lastId == 0){
            try {
                if (Files.exists(Path.of("specialty.lastId"))) {
                    lastId = Long.parseLong(Files.readString(Path.of("specialty.lastId")));
                }
            } catch (IOException e) {
                System.out.println("oops! there is some io exception "+e.getMessage());
            }
        }
    }

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
        status = Status.getStatus(statusId);
    }


    public static long getLastId() {
        return lastId;
    }

    @Override
    public long getStaticLastId() {//for using in MyGenericRepositoryImpl
        return Specialty.lastId;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setNewId() {
        AtomicLong l = new AtomicLong(lastId);
        Specialty.lastId = l.incrementAndGet();
        this.id = lastId;
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
        name = name;
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
