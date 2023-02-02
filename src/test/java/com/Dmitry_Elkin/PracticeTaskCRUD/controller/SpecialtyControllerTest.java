package com.Dmitry_Elkin.PracticeTaskCRUD.controller;


import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.SpecialtyRepository;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.SpecialtyRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class SpecialtyControllerTest {

    @Mock
    SpecialtyRepositoryImpl mockRepository;
    @Mock
    SpecialtyController mockSController;

    @Test
    void printItems(){

        List<Specialty> specialties = new LinkedList<>();
        specialties.add(new Specialty("Specialty 1"));
        specialties.add(new Specialty("Specialty 2"));

        SpecialtyRepository mockRepo = Mockito.mock(SpecialtyRepository.class);//создаем заглушку
        Mockito.when(mockRepo.getAll()).thenReturn(specialties);//задаем поведение для заглушки

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

    @Test
    public void createNewItem(){
        mockRepository.addOrUpdate(new Specialty(1,"Specialty with id = 1"));
        Mockito.verify(mockRepository).addOrUpdate(new Specialty(1,"Specialty with id = 1"));
    }

    @Test
    public void SControllerTest(){
        SpecialtyController controller = new SpecialtyController(mockRepository);

        InputStream inputStream = System.in;  // сохраняем ссылку на ввод с клавиатуры
//        byte[] b =  ByteBuffer.allocate(4).putInt(1).array();
//        byte[] b = BigInteger.valueOf(1).toByteArray();
//        System.setIn(new ByteArrayInputStream(b)); // подменяем ввод
        System.setIn(new ByteArrayInputStream("1\nTest Specialty\n0\n0\n".getBytes())); // подменяем ввод
        controller.menu();
        System.setIn(inputStream);            // подменяем обратно

        System.setIn(new ByteArrayInputStream("2\nTest Specialty\n0\n0\n".getBytes())); // подменяем ввод
        controller.menu();
        System.setIn(inputStream);            // подменяем обратно


//        Mockito.verify(mockSController).createNewItem();
        Mockito.verify(mockRepository).addOrUpdate(new Specialty("Test Specialty"));
    }
}