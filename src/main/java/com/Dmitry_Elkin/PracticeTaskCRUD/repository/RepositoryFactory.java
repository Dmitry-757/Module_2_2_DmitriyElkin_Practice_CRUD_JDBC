package com.Dmitry_Elkin.PracticeTaskCRUD.repository;


public class RepositoryFactory {
    private static final GsonSkillRepositoryImpl skillRepository;
    private static final GsonSpecialtyRepositoryImpl specialtyRepository;
    private static final GsonDeveloperRepositoryImpl developerRepository;

    static {
        skillRepository = new GsonSkillRepositoryImpl();
        specialtyRepository = new GsonSpecialtyRepositoryImpl();
        developerRepository = new GsonDeveloperRepositoryImpl();
    }

    public static GsonSkillRepositoryImpl getSkillRepository() {
        return skillRepository;
    }

    public static GsonSpecialtyRepositoryImpl getSpecialtyRepository() {
        return specialtyRepository;
    }

    public static GsonDeveloperRepositoryImpl getDeveloperRepository() {
        return developerRepository;
    }
}
