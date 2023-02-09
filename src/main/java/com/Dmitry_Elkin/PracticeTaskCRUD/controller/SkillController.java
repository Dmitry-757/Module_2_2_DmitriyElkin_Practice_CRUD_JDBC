package com.Dmitry_Elkin.PracticeTaskCRUD.controller;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.SkillRepository;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.jdbc.SkillRepositoryImpl;

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

    public Skill insert(Skill item){
        return repository.insert(item);
    }

    public Skill update(Skill item){
        return repository.update(item);
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
