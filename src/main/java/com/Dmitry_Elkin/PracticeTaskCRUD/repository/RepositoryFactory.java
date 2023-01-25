package com.Dmitry_Elkin.PracticeTaskCRUD.repository;


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
