package com.Dmitry_Elkin.PracticeTaskCRUD.repository;


import com.Dmitry_Elkin.PracticeTaskCRUD.repository.jdbc.DeveloperRepositoryImpl;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.jdbc.SkillRepositoryImpl;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.jdbc.SpecialtyRepositoryImpl;

public class RepositoryFactory {
    private static final SkillRepositoryImpl skillRepository;
    private static final SpecialtyRepositoryImpl specialtyRepository;
    private static final DeveloperRepositoryImpl developerRepository;

    static {
        skillRepository = new SkillRepositoryImpl();
        specialtyRepository = new SpecialtyRepositoryImpl();
        developerRepository = new DeveloperRepositoryImpl();
    }

    public static SkillRepositoryImpl getSkillRepository() {
        return skillRepository;
    }

    public static SpecialtyRepositoryImpl getSpecialtyRepository() {
        return specialtyRepository;
    }

    public static DeveloperRepositoryImpl getDeveloperRepository() {
        return developerRepository;
    }
}
