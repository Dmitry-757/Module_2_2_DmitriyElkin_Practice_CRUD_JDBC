package com.Dmitry_Elkin.PracticeTaskCRUD.controller;


import com.Dmitry_Elkin.PracticeTaskCRUD.model.Developer;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.*;

import java.util.HashSet;

import static com.Dmitry_Elkin.PracticeTaskCRUD.controller.MainController.sc;
import static com.Dmitry_Elkin.PracticeTaskCRUD.controller.Service.*;

public class DeveloperController {

    private static final DeveloperRepository repository = RepositoryFactory.getDeveloperRepository();

    //************* menu ********************
    public static void menu() {
        boolean goBack = false;
        while (!goBack) {
            System.out.println("1 - New item, 2 - change item, 3 - Delete item, 4 - UnDelete item, " +
                    "5 - print all items, 6 - print Active items, 7 - print Deleted items, 0 - go back");
            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> createNewItem();
                    case 2 -> changeItem();
                    case 3 -> deleteItem();
                    case 4 -> unDeleteItem();
                    case 5 -> printItems(null);
                    case 6 -> printItems(Status.ACTIVE);
                    case 7 -> printItems(Status.DELETED);
                    case 0 -> goBack = true;
                    default -> System.out.println("Wrong input!");
                }
            } else {
                System.out.println("wrong input... Please, use only digits!");
                sc.nextLine();
            }
        }
    }

    private static void createNewItem() {
        String firstName = getStringParamFromConsole("first name");
        String lastName = getStringParamFromConsole("second name");
        HashSet<Skill> skills = new HashSet<>(getGenericListFromConsole("Skill", RepositoryFactory.getSkillRepository()));
        Specialty specialty = getGenericParamFromConsole("Specialty", RepositoryFactory.getSpecialtyRepository());
        repository.addOrUpdate(new Developer(firstName, lastName, skills, specialty));
    }



    private static void changeItem() {
        Developer item = getGenericParamFromConsole("Developer", repository);
        if (item != null) {
            System.out.println("editing item = " + item);
            String firstName = getStringParamFromConsole("first name");
            String lastName = getStringParamFromConsole("second name");
            HashSet<Skill> skills = new HashSet<>(getGenericListFromConsole("Skills", RepositoryFactory.getSkillRepository()));
            Specialty specialty = getGenericParamFromConsole("Specialty", RepositoryFactory.getSpecialtyRepository());
            item.setFirstName(firstName);
            item.setLastName(lastName);
            if (skills.size() != 0) {
                item.setSkills(skills);
            }
            if (specialty != null) {
                item.setSpecialty(specialty);
            }
            repository.addOrUpdate(item);
        }

    }

    public static void printItems(Status status) {
        Service.printItems(status, repository);
    }


    private static void deleteItem() {
        Developer item = getGenericParamFromConsole("Developer", repository, Status.ACTIVE);
        if (item != null) {
            System.out.println("deleting item is : " + item);
            repository.delete(item);
        }

    }

    private static void unDeleteItem() {
        Developer item = getGenericParamFromConsole("Developer", repository, Status.DELETED);
        if (item != null) {
            System.out.println("UnDeleting item is : " + item);
            repository.unDelete(item);
        }
    }
}
