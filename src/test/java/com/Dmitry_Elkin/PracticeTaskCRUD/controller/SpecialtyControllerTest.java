package com.Dmitry_Elkin.PracticeTaskCRUD.controller;


import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;


@ExtendWith(MockitoExtension.class)
@RunWith(PowerMockRunner.class)
class SpecialtyControllerTest {

    @Test
    void printItems(){

//        SpecialtyController scMock = Mockito.mock(SpecialtyController.class);

        SpecialtyController scMock = spy(new SpecialtyController());
        Specialty specialty = new Specialty("Test");
        try {
            when(scMock,"printItems").thenReturn(specialty);
        } catch (Exception e) {
            System.out.println("exception! "+e.getMessage());
        }

//        Mockito.when(specialtyController.createNewItem())
//        mockRepository.addOrUpdate(specialty);
    }

}