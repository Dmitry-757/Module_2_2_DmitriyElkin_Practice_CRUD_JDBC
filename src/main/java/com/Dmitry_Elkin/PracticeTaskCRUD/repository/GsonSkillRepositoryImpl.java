package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class GsonSkillRepositoryImpl implements SkillRepository {
//    private static final String fileName = "skills.json";
//    private static final String tmpFileName = "skills.tmp";
//    private static final Path file = Paths.get(fileName);
//    private static final Path tmpFile = Path.of(tmpFileName);




    @Override
    public List<Skill> getAll(Status status) {
        List<Skill> itemList = new LinkedList<>();
        return itemList;
    }

    //чтоб не переписывать код, где вызывается метод без параметров
    @Override
    public List<Skill> getAll() {
        return getAll(null);
    }



    @Override
    public Skill getById(Long id) {
        return null;
    }

    @Override
    public void addOrUpdate(Skill item) {
        //*** add ***
        if (item.getId() <= 0) {
            item.setNewId();
            add(item);
        } else {
            //*** update ***
            update(item);
        }
    }


    public void add(Skill item) {
    }

    public void update(Skill item) {

    }

    @Override
    public void delete(Skill item) {
        item.setDeleted();
        update(item);
    }

    public void unDelete(Skill item) {
        item.setUnDeleted();
        update(item);
    }

}
