package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.jdbc.SkillRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class GsonSkillRepositoryImplTest {
    Path file = Paths.get("skill.json");
    Skill skill1 = new Skill("Linear algebra");
    Skill skill2 = new Skill("java EE");
    SkillRepositoryImpl gsonSkillRepository = new SkillRepositoryImpl();



    @Test
    void addOrUpdate() {

        System.out.println("****** add **********");
        gsonSkillRepository.addOrUpdate(skill1);
        gsonSkillRepository.addOrUpdate(skill2);

        try {
            List<String> list = Files.readAllLines(file);
            list.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n"+"***** update now ********");

        skill2.setName(skill2.getName()+" updated");
        skill2.setId(2);
        gsonSkillRepository.addOrUpdate(skill2);

        try {
            List<String> list = Files.readAllLines(file);
            list.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    void add() {

        Skill skill = new Skill("Algorithms");
        System.out.println("****** add **********");
        gsonSkillRepository.addOrUpdate(skill);

        try {
            List<String> list = Files.readAllLines(file);
            list.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void getAll() {
        List<Skill> list = gsonSkillRepository.getAll();
        list.forEach(System.out::println);
//        skill1.setId(1);
//        skill2.setId(2);
//        skill2.setName(skill2.getName()+" updated");
//        assertThat(list)
//                .containsAll(List.of(skill1, skill2));
    }

    @Test
    void getById() {
        skill1.setId(1);
        Skill skill = gsonSkillRepository.getById(1L);
        System.out.println(skill);
        assertThat(skill).isEqualTo(skill1);
    }

    @Test
    void delete() {
        skill1.setId(1);
        skill2.setId(2);
        gsonSkillRepository.delete(skill2);
        List<Skill> list = gsonSkillRepository.getAll();
        list.forEach(System.out::println);
        assertThat(list)
                .containsAll(List.of(skill1, skill2));
    }

    @Test
    void unDelete() {
        skill1.setId(1);
        skill2.setId(2);
        gsonSkillRepository.unDelete(skill2);
        List<Skill> list = gsonSkillRepository.getAll();
        list.forEach(System.out::println);
        assertThat(list)
                .containsAll(List.of(skill1, skill2));
    }

}