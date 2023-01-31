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


@ExtendWith(MockitoExtension.class)
class SpecialtyControllerTest {

    @Test
    void printItems(){

        SpecialtyRepository mockRepo = Mockito.mock(SpecialtyRepository.class);
        List<Specialty> specialties = new LinkedList<>();
        specialties.add(new Specialty("Specialty 1"));
        specialties.add(new Specialty("Specialty 2"));

        Mockito.when(mockRepo.getAll()).thenReturn(specialties);

        Service.printItems(mockRepo.getAll());
    }

    @Test
    void printItemsWithPowerMock() throws Exception {
        SpecialtyController sc = new SpecialtyController();
        Whitebox.invokeMethod(sc,"printItems", null);
    }

}