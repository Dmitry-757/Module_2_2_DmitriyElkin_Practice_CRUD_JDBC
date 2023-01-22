package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Developer;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.NonTechTask.myRepository.MyGenericRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class MyGenericRepositoryImplTest {


    @Test
    void addOrUpdateSpecialty() {
        Path file = Paths.get("specialty.json");
        Specialty item1 = new Specialty("developer");
        Specialty item2 = new Specialty("qa");

        MyGenericRepositoryImpl<Specialty> gsonRepository = new MyGenericRepositoryImpl<>(Specialty.class);

        System.out.println("****** add **********");
        gsonRepository.addOrUpdate(item1);
        gsonRepository.addOrUpdate(item2);

        try {
            List<String> list = Files.readAllLines(file);
            list.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n"+"***** update now ********");

        item2.setName(item2.getName()+" updated");
        item2.setId(2);
        gsonRepository.addOrUpdate(item2);

        try {
            List<String> list = Files.readAllLines(file);
            list.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void getAllSpecialty() {
        Specialty item1 = new Specialty("developer");
        Specialty item2 = new Specialty("qa");

        MyGenericRepositoryImpl<Specialty> gsonRepository = new MyGenericRepositoryImpl<>(Specialty.class);
        List<Specialty> list = gsonRepository.getAll();
        list.forEach(System.out::println);
        item1.setId(1);
        item2.setId(2);
        item2.setName(item2.getName()+" updated");

        assertThat(list)
                .containsAll(List.of(item1, item2));

    }


    @Test
    void addOrUpdateSkill() {
        MyGenericRepositoryImpl<Skill> gsonRepository = new MyGenericRepositoryImpl<>(Skill.class);
        Path file = Paths.get("skill.json");
        Skill item1 = new Skill("linear algebra");
        Skill item2 = new Skill("c#");


        System.out.println("****** add **********");
        gsonRepository.addOrUpdate(item1);
        gsonRepository.addOrUpdate(item2);

        try {
            List<String> list = Files.readAllLines(file);
            list.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n"+"***** update now ********");

        item2.setName(item2.getName()+" updated");
        item2.setId(2);
        gsonRepository.addOrUpdate(item2);

        try {
            List<String> list = Files.readAllLines(file);
            list.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void getAllSkill() {
        MyGenericRepositoryImpl<Skill> gsonRepository = new MyGenericRepositoryImpl<>(Skill.class);

        Skill item1 = new Skill("linear algebra");
        Skill item2 = new Skill("c#");

        List<Skill> list = gsonRepository.getAll();
        list.forEach(System.out::println);
        item1.setId(1);
        item2.setId(2);
        item2.setName(item2.getName()+" updated");

        assertThat(list)
                .containsAll(List.of(item1, item2));

    }


    @Test
    void addOrUpdateDeveloper() {
        MyGenericRepositoryImpl<Developer> gsonRepository = new MyGenericRepositoryImpl<>(Developer.class);
        MyGenericRepositoryImpl<Specialty> gsonSpecialtyRepository = new MyGenericRepositoryImpl<>(Specialty.class);
        Path file = Paths.get("developer.json");
        Developer item1 = new Developer("Vasja", "Pupkin",gsonSpecialtyRepository.getById(1L));
        Developer item2 = new Developer("Petja","Pitkin",gsonSpecialtyRepository.getById(2L));


        System.out.println("****** add **********");
        gsonRepository.addOrUpdate(item1);
        gsonRepository.addOrUpdate(item2);

        try {
            List<String> list = Files.readAllLines(file);
            list.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n"+"***** update now ********");

        item2.setLastName(item2.getLastName()+" updated");
        item2.setId(2);
        gsonRepository.addOrUpdate(item2);

        try {
            List<String> list = Files.readAllLines(file);
            list.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void getAllDeveloper() {
        MyGenericRepositoryImpl<Developer> gsonRepository = new MyGenericRepositoryImpl<>(Developer.class);

        List<Developer> list = gsonRepository.getAll();
        list.forEach(System.out::println);
    }

}