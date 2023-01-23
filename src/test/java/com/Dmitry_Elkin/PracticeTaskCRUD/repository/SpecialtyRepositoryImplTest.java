package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecialtyRepositoryImplTest {

    @Test
    void getAll() {
        System.out.println((new SpecialtyRepositoryImpl()).getAll());
        System.out.println((new SpecialtyRepositoryImpl()).getAll(Status.ACTIVE));
        System.out.println((new SpecialtyRepositoryImpl()).getAll(Status.DELETED));
    }
}