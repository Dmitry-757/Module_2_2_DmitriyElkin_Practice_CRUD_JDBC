package com.Dmitry_Elkin.PracticeTaskCRUD.controller;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.RepositoryFactory;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.SkillRepository;


import static com.Dmitry_Elkin.PracticeTaskCRUD.controller.MainController.sc;
import static com.Dmitry_Elkin.PracticeTaskCRUD.controller.Service.getGenericParamFromConsole;
import static com.Dmitry_Elkin.PracticeTaskCRUD.controller.Service.getStringParamFromConsole;


public class SkillController {

    private static final SkillRepository repository = RepositoryFactory.getSkillRepository();

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
        String name = getStringParamFromConsole("first name");
        repository.addOrUpdate(new Skill(name));
    }

    private static void changeItem() {
        Skill item = getGenericParamFromConsole("Skill", repository);
        if (item != null) {
            System.out.println("editing item = " + item);
            String newName = getStringParamFromConsole("name");
            item.setName(newName);
            repository.addOrUpdate(item);
        }
    }

    private static void printItems(Status status) {
        Service.printItems(status, repository);
    }


    private static void deleteItem() {

        Skill item = getGenericParamFromConsole("Skill", repository, Status.ACTIVE);
        if (item != null) {
            System.out.println("deleting item is : " + item);
            repository.delete(item);
        }
    }

    private static void unDeleteItem() {
        Skill item = getGenericParamFromConsole("Skill", repository, Status.DELETED);
        if (item != null) {
            System.out.println("UnDeleting item is : " + item);
            repository.unDelete(item);
        }
    }

    //*****************************************************
}
