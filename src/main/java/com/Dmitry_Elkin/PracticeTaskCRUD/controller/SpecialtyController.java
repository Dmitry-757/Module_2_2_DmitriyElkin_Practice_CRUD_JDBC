package com.Dmitry_Elkin.PracticeTaskCRUD.controller;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.SpecialtyRepository;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.jdbc.SpecialtyRepositoryImpl;

import java.util.List;

public class SpecialtyController {
    private final SpecialtyRepository repository = new SpecialtyRepositoryImpl();

    public Specialty getById(long id){
        return repository.getById(id);
    }
    public List<Specialty> getAll(Status status){
        return repository.getAll(status);
    }
    public List<Specialty> getAll(){
        return repository.getAll();
    }

    public void insert(String name){
        Specialty item = new Specialty(name);
        repository.addOrUpdate(item);
    }

    public void update(Specialty item){
        if (item != null) {
            repository.addOrUpdate(item);
        }
    }

    public void delete(Specialty item){
        item.setDeleted();
        update(item);
    }

    public void unDelete(Specialty item){
        item.setUnDeleted();
        update(item);
    }

}
