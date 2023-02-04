package com.Dmitry_Elkin.PracticeTaskCRUD.controller;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.SkillRepository;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.SkillRepositoryImpl;

import java.util.List;

public class SkillController {
    private final SkillRepository repository = new SkillRepositoryImpl();
    public Skill getById(long id){
        return repository.getById(id);
    }
    public List<Skill> getAll(){
        return repository.getAll();
    }
    public List<Skill> getAll(Status status){
        return repository.getAll(status);
    }

    public void insert(Skill item){
        repository.addOrUpdate(item);
    }

    public void update(Skill item){
        repository.addOrUpdate(item);
    }

    public void delete(Skill item){
        item.setDeleted();
        update(item);
    }

    public void unDelete(Skill item){
        item.setUnDeleted();
        update(item);
    }

}
