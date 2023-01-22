package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import java.util.LinkedList;
import java.util.List;

public class GsonSpecialtyRepositoryImpl implements SpecialtyRepository {
    @Override
    public List<Specialty> getAll(Status status) {
        List<Specialty> itemList = new LinkedList<>();
        return itemList;
    }

    //чтоб не переписывать код, где вызывается метод без параметров
    @Override
    public List<Specialty> getAll() {
        return getAll(null);
    }

    @Override
    public Specialty getById(Long id) {

        return null;
    }

    @Override
    public void addOrUpdate(Specialty item) {
        //*** add ***
        if (item.getId() <= 0) {
            item.setNewId();
            add(item);
        }

        //*** update ***
        update(item);
    }


    public void add(Specialty item){
    }

    public void update(Specialty item){

    }

    @Override
    public void delete(Specialty item) {
        item.setDeleted();
        update(item);
    }

    @Override
    public void unDelete(Specialty item) {
        item.setUnDeleted();
        update(item);
    }


}
