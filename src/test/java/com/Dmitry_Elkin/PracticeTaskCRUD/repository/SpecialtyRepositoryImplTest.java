package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import org.junit.jupiter.api.Test;

class SpecialtyRepositoryImplTest {

    @Test
    void getAll() {
        System.out.println((new SpecialtyRepositoryImpl()).getAll());
        System.out.println((new SpecialtyRepositoryImpl()).getAll(Status.ACTIVE));
        System.out.println((new SpecialtyRepositoryImpl()).getAll(Status.DELETED));
    }
    @Test
    void add(){
        Specialty specialty = new Specialty("c# developer");
        (new SpecialtyRepositoryImpl()).insert(
                specialty
        );
        System.out.println("new item is " + specialty);
    }

    @Test
    void update(){
        SpecialtyRepositoryImpl repository = new SpecialtyRepositoryImpl();
        Specialty specialty = repository.getById(16L);
        specialty.setName("c# gov dev");
        specialty.setDeleted();
        repository.update(specialty);

        specialty = repository.getById(16L);
        System.out.println("updated item is " + specialty.toString());
    }

    @Test
    void delete(){
        SpecialtyRepositoryImpl repository = new SpecialtyRepositoryImpl();
        Specialty specialty = repository.getById(16L);
        specialty.setDeleted();

        System.out.println("deleted item is " + specialty);
    }

    @Test
    void unDelete(){
        SpecialtyRepositoryImpl repository = new SpecialtyRepositoryImpl();
        Specialty specialty = repository.getById(16L);
        specialty.setUnDeleted();

        System.out.println("unDeleted item is " + specialty);
    }


}