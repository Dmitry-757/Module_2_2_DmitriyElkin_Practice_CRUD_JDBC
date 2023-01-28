package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Developer;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.HashSet;


class DeveloperRepositoryImplTest {
    SpecialtyRepositoryImpl specialtyRepository = new SpecialtyRepositoryImpl();
    SkillRepositoryImpl skillRepository = new SkillRepositoryImpl();
    DeveloperRepositoryImpl developerRepository = new DeveloperRepositoryImpl();

    @Test
    void getAll() {
        developerRepository.getAll(null).forEach(System.out::println);
    }

    @Test
    void getById() {
        Developer developerReal = developerRepository.getById(2L);
        System.out.println(developerReal);
    }

    @Test
    void insert() {

        Specialty specialty = specialtyRepository.getById(1L);
        HashSet<Skill> skills = new HashSet<>();
        skills.add(skillRepository.getById(1L));
        skills.add(skillRepository.getById(2L));

        Developer developerExpected = new Developer("Ilon", "Mask", skills, specialty);
        developerRepository.insert(developerExpected);

        Developer developerReal = developerRepository.getById(developerExpected.getId());

        assertThat(developerReal)
                .isEqualTo(developerExpected);

    }


    @Test
    void update() {

        HashSet<Skill> skills = new HashSet<>();
        skills.add(skillRepository.getById(1L));
        skills.add(skillRepository.getById(3L));

        Developer developer = developerRepository.getById(1L);
        System.out.println(developer);

        developer.setSkills(skills);
        developerRepository.update(developer);
        Developer developerReal = developerRepository.getById(1L);
        System.out.println(developerReal);

    }

}