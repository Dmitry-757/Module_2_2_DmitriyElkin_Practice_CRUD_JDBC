package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Developer;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;

import java.util.List;

public interface DeveloperRepository extends GenericRepository<Developer, Long>{
    @Override
    Developer insert(Developer item);
    @Override
    Developer update(Developer item);

    @Override
    List<Developer> getAll(Status status);

    @Override
    List<Developer> getAll();

    @Override
    Developer getById(Long id);

    @Override
    void delete(Developer item);

    @Override
    void unDelete(Developer item);
}
