package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Developer;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import java.util.LinkedList;
import java.util.List;

public class GsonDeveloperRepositoryImpl implements DeveloperRepository {
//    private static final String fileName = "skills.json";
//    private static final String tmpFileName = "skills.tmp";
//    private static final Path file = Paths.get(fileName);
//    private static final Path tmpFile = Path.of(tmpFileName);






    @Override
    public List<Developer> getAll(Status status) {
        List<Developer> itemList = new LinkedList<>();
        return itemList;
    }
    //чтоб не переписывать код, где вызывается метод без параметров
    @Override
    public List<Developer> getAll() {
        return getAll(null);
    }

    @Override
    public Developer getById(Long id) {
        return null;
    }

    @Override
    public void addOrUpdate(Developer item) {
        //*** add ***
        if (item.getId() <= 0) {
            item.setNewId();
            add(item);
        }else {
           //*** update ***
            update(item);
        }
    }


    public void add(Developer item){
    }

    public void update(Developer item){

    }

    @Override
    public void delete(Developer item) {
        item.setDeleted();
        update(item);
    }

    public void unDelete(Developer item) {
        item.setUnDeleted();
        update(item);
    }

}
