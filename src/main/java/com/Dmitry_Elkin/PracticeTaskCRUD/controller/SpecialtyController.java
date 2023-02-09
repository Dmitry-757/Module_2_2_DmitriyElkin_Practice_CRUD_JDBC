package com.Dmitry_Elkin.PracticeTaskCRUD.controller;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.SpecialtyRepository;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.jdbc.SpecialtyRepositoryImpl;

import java.util.List;

public class SpecialtyController {
    private final SpecialtyRepository repository = new SpecialtyRepositoryImpl();

    public Specialty getById(long id) {
        return repository.getById(id);
    }

    public List<Specialty> getAll(Status status) {
        return repository.getAll(status);
    }

    public List<Specialty> getAll() {
        return repository.getAll();
    }

    public Specialty insert(String name) {
        Specialty item = new Specialty(name);
        return repository.insert(item);
    }

    public Specialty update(Specialty item) {
        return repository.update(item);
    }

    public void delete(Specialty item) {
        item.setDeleted();
        update(item);
    }

    public void unDelete(Specialty item) {
        item.setUnDeleted();
        update(item);
    }

}
