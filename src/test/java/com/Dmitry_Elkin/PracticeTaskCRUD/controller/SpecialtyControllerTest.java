package com.Dmitry_Elkin.PracticeTaskCRUD.controller;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class SpecialtyControllerTest {

    @Test
    void createNewItem(){

        SpecialtyController specialtyController = Mockito.mock(SpecialtyController.class);

        Specialty specialty = new Specialty("Test");

//        Mockito.when(specialtyController.createNewItem())

//        mockRepository.addOrUpdate(specialty);
    }

}