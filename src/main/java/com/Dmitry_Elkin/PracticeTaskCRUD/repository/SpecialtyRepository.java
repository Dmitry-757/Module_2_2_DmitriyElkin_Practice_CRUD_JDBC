package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;

import java.util.List;

public interface SpecialtyRepository extends GenericRepository<Specialty, Long> {

    @Override
    Specialty insert(Specialty item);
    @Override
    Specialty update(Specialty item);

    @Override
    List<Specialty> getAll(Status status);

    @Override
    List<Specialty> getAll();

    @Override
    Specialty getById(Long id);

    @Override
    void delete(Specialty item);

    @Override
    void unDelete(Specialty item);
}
