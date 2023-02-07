package com.Dmitry_Elkin.PracticeTaskCRUD.controller;


import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.jdbc.SkillRepositoryImpl;
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
class SkillsControllerTest {


    @Mock
    SkillRepositoryImpl mockRepository;

    SkillController controller;

    @BeforeEach
    void setUp() {
        controller = new SkillController();
        Field repository;
        try {
            repository = controller.getClass()
                    .getDeclaredField("repository");
            repository.setAccessible(true);
            repository.set(controller, mockRepository);
        } catch (NoSuchFieldException e) {
            System.out.println(e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    void getAllItemsTest(){

        List<Skill> items = new LinkedList<>();
        items.add(new Skill("Skill 1"));
        items.add(new Skill("Skill 2"));

        Mockito.when(mockRepository.getAll()).thenReturn(items);//задаем поведение для заглушки

        assertThat(controller.getAll()).isEqualTo(items);
        Mockito.verify(mockRepository).getAll();
    }

    @Test
    void getItemByIdTest(){

        Skill item = new Skill("Skill 1");
        Mockito.when(mockRepository.getById(1L)).thenReturn(item);//задаем поведение для заглушки

        assertThat(controller.getById(1L)).isEqualTo(item);
        Mockito.verify(mockRepository).getById(1L);
    }


    @Test
    public void createNewItemTest(){
        controller.insert(new Skill("Test Skill"));
        Mockito.verify(mockRepository).addOrUpdate(new Skill("Test Skill"));
    }

    @Test
    public void updateItemTest(){
        controller.update(new Skill("updated Skill"));
        Mockito.verify(mockRepository).addOrUpdate(new Skill("updated Skill"));
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