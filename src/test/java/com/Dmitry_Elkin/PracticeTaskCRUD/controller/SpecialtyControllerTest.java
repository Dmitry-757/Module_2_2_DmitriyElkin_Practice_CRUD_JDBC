package com.Dmitry_Elkin.PracticeTaskCRUD.controller;


import com.Dmitry_Elkin.PracticeTaskCRUD.controller.SpecialtyController;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.SpecialtyRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(MockitoExtension.class)
class SpecialtyControllerTest {


    @Mock
    SpecialtyRepositoryImpl mockRepository;

    SpecialtyController specialtyController;

    @BeforeEach
    void setUp() {
        specialtyController = new SpecialtyController();
        Field repository;
        try {
            repository = specialtyController.getClass()
                    .getDeclaredField("repository");
            repository.setAccessible(true);
            repository.set(specialtyController, mockRepository);
        } catch (NoSuchFieldException e) {
            System.out.println(e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    void getAllItemsTest(){

        List<Specialty> specialties = new LinkedList<>();
        specialties.add(new Specialty("Specialty 1"));
        specialties.add(new Specialty("Specialty 2"));

        Mockito.when(mockRepository.getAll()).thenReturn(specialties);//задаем поведение для заглушки

        assertThat(specialtyController.getAll()).isEqualTo(specialties);
        Mockito.verify(mockRepository).getAll();
    }

    @Test
    void getItemByIdTest(){

        Specialty sp = new Specialty("Specialty 1");
        Mockito.when(mockRepository.getById(1L)).thenReturn(sp);//задаем поведение для заглушки

        assertThat(specialtyController.getById(1L)).isEqualTo(sp);
        Mockito.verify(mockRepository).getById(1L);
    }


    @Test
    public void createNewItemTest(){
        specialtyController.insert("Test Specialty");
        Mockito.verify(mockRepository).addOrUpdate(new Specialty("Test Specialty"));
    }

    @Test
    public void updateItemTest(){
        specialtyController.update(new Specialty("updated Specialty"));
        Mockito.verify(mockRepository).addOrUpdate(new Specialty("updated Specialty"));
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



}