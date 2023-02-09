package com.Dmitry_Elkin.PracticeTaskCRUD.controller;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Developer;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.DeveloperRepository;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.jdbc.DeveloperRepositoryImpl;

import java.util.List;

public class DeveloperController {
    private final DeveloperRepository repository = new DeveloperRepositoryImpl();
    public Developer getById(long id){
        return repository.getById(id);
    }
    public List<Developer> getAll(Status status){
        return repository.getAll(status);
    }
    public List<Developer> getAll(){
        return repository.getAll();
    }

    public Developer insert(Developer item){
        return repository.insert(item);
    }

    public Developer update(Developer item){
        return repository.update(item);
    }

    public void delete(Developer item){
        item.setDeleted();
        update(item);
    }

    public void unDelete(Developer item){
        item.setUnDeleted();
        update(item);
    }

}
