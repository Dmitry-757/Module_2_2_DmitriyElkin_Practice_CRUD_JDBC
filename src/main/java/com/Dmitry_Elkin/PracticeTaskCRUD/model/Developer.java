package com.Dmitry_Elkin.PracticeTaskCRUD.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

//implements BaseModelsMethsI - for my experiment by creating common repository
//meth getStaticLastId() is needed  for possibility to get lastId from instance of model-class
public class Developer implements BaseModelsMethsI {
    private static volatile long lastId;
    static{
        if (lastId == 0){
            try {
                if (Files.exists(Path.of("developer.lastId"))) {
                    lastId = Long.parseLong(Files.readString(Path.of("developer.lastId")));
                }
            } catch (IOException e) {
                System.out.println("oops! there is some io exception "+e.getMessage());
            }
        }
    }

    private long id;
    private String firstName;
    private String lastName;
    private HashSet<Skill> skills;
    private Specialty specialty;
    private Status status;

    public Developer(long id, String firstName, String lastName, HashSet<Skill> skills, Specialty specialty, Status status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.skills = skills;
        this.specialty = specialty;
        this.status = status;
    }

    public Developer(String firstName, String lastName, HashSet<Skill> skills, Specialty specialty) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.skills = skills;
        this.specialty = specialty;
        this.status = Status.ACTIVE;
    }

    public Developer(String firstName, String lastName, Specialty specialty) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialty = specialty;
        this.status = Status.ACTIVE;
    }

    public static long getLastId() {
        return lastId;
    }
    @Override
    public long getStaticLastId() {//for using in MyGenericRepositoryImpl
        return Developer.lastId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNewId() {
        AtomicLong l = new AtomicLong(lastId);
        Developer.lastId = l.incrementAndGet();
        this.id = lastId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public HashSet<Skill> getSkills() {
        return skills;
    }

    public void setSkills(HashSet<Skill> skills) {
        this.skills = skills;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public Status getStatus() {
        return status;
    }

    //*** for compatability with interface BaseModelsMethsI ***
    @Override
    public String getName() {
        return getFirstName();
    }
    @Override
    public void setName(String name) {
        setFirstName(name);
    }
    //*****************************************************

    public void setDeleted() {
        status = Status.DELETED;
    }

    public void setUnDeleted() {
        status = Status.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return id == developer.id && firstName.equals(developer.firstName) && lastName.equals(developer.lastName) && Objects.equals(skills, developer.skills) && specialty.equals(developer.specialty) && status == developer.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, skills, specialty, status);
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", firstName = '" + firstName + '\'' +
                ", lastName = '" + lastName + '\'' +
                ", skills = " +
                skills.stream().map(Object::toString).collect(Collectors.joining(", ")) +
                ", specialty = " + (specialty != null ? specialty.getName() : "SpecialtyLess ((") +
                ", status = " + status +
                '}';
    }
}
