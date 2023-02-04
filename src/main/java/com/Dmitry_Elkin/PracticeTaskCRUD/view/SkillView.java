package com.Dmitry_Elkin.PracticeTaskCRUD.view;

import com.Dmitry_Elkin.PracticeTaskCRUD.controller.SkillController;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;


import static com.Dmitry_Elkin.PracticeTaskCRUD.view.MainView.sc;
import static com.Dmitry_Elkin.PracticeTaskCRUD.view.ConsoleService.getStringParamFromConsole;


public class SkillView {

//    private static final SkillRepository repository = RepositoryFactory.getSkillRepository();
    private final SkillController controller = new SkillController();


    //************* menu ********************
    public void menu() {
        boolean goBack = false;
        while (!goBack) {
            System.out.println("1 - New item, 2 - change item, 3 - Delete item, 4 - UnDelete item, " +
                    "5 - print all items, 6 - print Active items, 7 - print Deleted items, 8 - print item by Id, 0 - go back");
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
                    case 8 -> printItemsById();
                    case 0 -> goBack = true;
                    default -> System.out.println("Wrong input!");
                }
            } else {
                System.out.println("wrong input... Please, use only digits!");
                sc.nextLine();
            }
        }
    }

    private void createNewItem() {
        String name = getStringParamFromConsole("first name");
        controller.insert(new Skill(name));
    }

    private void changeItem() {
//        Skill item = getGenericParamFromConsole("Skill", repository);
        Skill item = ConsoleService.getItemFromConsole("Skill",controller.getAll());

        if (item != null) {
            System.out.println("editing item = " + item);
            String newName = getStringParamFromConsole("name");
            item.setName(newName);
            controller.update(item);
        }
    }

//    private void printItems(Status status) {
//        Service.printItems(status, repository);
//    }
    private void printItems(Status status) {
        ConsoleService.printItems(controller.getAll(status));
    }

    private void printItemsById() {
        long id = ConsoleService.getIntParamFromConsole("id ");
        ConsoleService.printItems(controller.getById(id));
    }

    private void deleteItem() {

        Skill item = ConsoleService.getItemFromConsole("Skill",controller.getAll(Status.ACTIVE));
        if (item != null) {
            System.out.println("deleting item is : " + item);
            controller.delete(item);
        }
    }

    private void unDeleteItem() {
        Skill item = ConsoleService.getItemFromConsole("Skill",controller.getAll(Status.DELETED));
        if (item != null) {
            System.out.println("UnDeleting item is : " + item);
            controller.unDelete(item);
        }
    }

    //*****************************************************
}
