package com.Dmitry_Elkin.PracticeTaskCRUD.controller;


import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.reflect.Whitebox;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class SpecialtyControllerTest {

    @Test
    void printItems(){

        List<Specialty> specialties = new LinkedList<>();
        specialties.add(new Specialty("Specialty 1"));
        specialties.add(new Specialty("Specialty 2"));

        SpecialtyRepository mockRepo = Mockito.mock(SpecialtyRepository.class);
        Mockito.when(mockRepo.getAll()).thenReturn(specialties);

        ConsoleService.printItems(mockRepo.getAll());
    }

    @Test
    void printItemsById(){

        SpecialtyRepository mockRepo = Mockito.mock(SpecialtyRepository.class);
        Mockito.when(mockRepo.getById(1L)).thenReturn(new Specialty(1,"Specialty with id = 1"));

        ConsoleService.printItems(mockRepo.getById(1L));
    }

    @Test
    void printItemsWithPowerMock() throws Exception {
        SpecialtyController sc = new SpecialtyController();
        Whitebox.invokeMethod(sc,"printItems", null);
//        String actualValue = Whitebox.invokeMethod(sc,"methodName", "arguments");
//        assertEquals(expected , actual);
    }

}