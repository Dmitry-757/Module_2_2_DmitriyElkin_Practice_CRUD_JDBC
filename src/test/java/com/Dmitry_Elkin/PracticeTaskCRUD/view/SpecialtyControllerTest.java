package com.Dmitry_Elkin.PracticeTaskCRUD.view;


import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.SpecialtyRepository;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.SpecialtyRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class SpecialtyControllerTest {


    @Mock
    SpecialtyRepositoryImpl mockRepository;
//    SpecialtyView controller = new SpecialtyView(mockRepository);

    @Test
    void printItems(){

        List<Specialty> specialties = new LinkedList<>();
        specialties.add(new Specialty("Specialty 1"));
        specialties.add(new Specialty("Specialty 2"));

        Mockito.when(mockRepository.getAll()).thenReturn(specialties);//задаем поведение для заглушки
    }

    @Test
    void printItemsById(){

        SpecialtyRepository mockRepo = Mockito.mock(SpecialtyRepository.class);
        Mockito.when(mockRepo.getById(1L)).thenReturn(new Specialty(1,"Specialty with id = 1"));

        ConsoleService.printItems(mockRepo.getById(1L));
    }

//    @Test
//    void printItemsWithPowerMock() throws Exception {
//        SpecialtyView sc = new SpecialtyView();
//        Whitebox.invokeMethod(sc,"printItems", null);
////        String actualValue = Whitebox.invokeMethod(sc,"methodName", "arguments");
////        assertEquals(expected , actual);
//    }

    @Test
    public void createNewItem(){
        mockRepository.addOrUpdate(new Specialty(1,"Specialty with id = 1"));
        Mockito.verify(mockRepository).addOrUpdate(new Specialty(1,"Specialty with id = 1"));
    }

//    @Test
//    public void SControllerTest_Insert(){
//        SpecialtyView controller = new SpecialtyView(mockRepository);
//
//        InputStream inputStream = System.in;  // сохраняем ссылку на ввод с клавиатуры
//        System.setIn(new ByteArrayInputStream("1\nTest Specialty\n0\n0\n".getBytes())); // подменяем ввод
//        controller.menu();
//        System.setIn(inputStream);            // подменяем обратно
//
//        Mockito.verify(mockRepository).addOrUpdate(new Specialty("Test Specialty"));
//    }

    @Test
    public void SControllerTest_Update(){

        InputStream inputStream = System.in;  // сохраняем ссылку на ввод с клавиатуры

        List<Specialty> specialties = new LinkedList<>();
        Specialty sp1 = new Specialty(1,"Specialty 1");
        Specialty sp2 = new Specialty(1,"Specialty 2");
        specialties.add(sp1);
        specialties.add(sp2);
//        Mockito.when(mockRepository.getAll()).thenReturn(specialties);//задаем поведение для заглушки
        Mockito.when(mockRepository.getAll(Status.ACTIVE)).thenReturn(specialties);//задаем поведение для заглушки
        Mockito.when(mockRepository.getById(1L)).thenReturn(sp1);//задаем поведение для заглушки
//        Mockito.when(mockRepository.getById(2L)).thenReturn(sp2);//задаем поведение для заглушки

//        MainView.sc.close();
        System.setIn(new ByteArrayInputStream("2\n1\nSpecialty 1\n0\n".getBytes())); // подменяем ввод
//        MainView.sc = new Scanner(System.in);
//        controller.menu();
        System.setIn(inputStream);            // подменяем обратно

        Mockito.verify(mockRepository).addOrUpdate(new Specialty(1,"Specialty 1"));
    }

//    @Test
//    public void SControllerTest_printItems(){
//        SpecialtyView controller = new SpecialtyView(mockRepository);
//
//        controller.menu();
//
////        Mockito.verify(mockRepository).addOrUpdate(new Specialty("Test Specialty"));
//    }


}