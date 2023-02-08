package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;

import java.util.List;

public interface GenericRepository<T,ID> {
    void insert(T item);
    void update(T item);
    List<T> getAll(Status status);
    //чтоб не переписывать код, где вызывается метод без параметров
    List<T> getAll();

    T getById(ID id);
    void delete(T item);
    void unDelete(T item);

}
