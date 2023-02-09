package com.Dmitry_Elkin.PracticeTaskCRUD.view;

import com.Dmitry_Elkin.PracticeTaskCRUD.controller.SpecialtyController;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;

import static com.Dmitry_Elkin.PracticeTaskCRUD.view.MainView.sc;

public class SpecialtyView {

    private final SpecialtyController controller = new SpecialtyController();
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

    void createNewItem() {
        String name = ConsoleService.getStringParamFromConsole("name");
        ConsoleService.printItems(controller.insert(name));
    }

    private void changeItem() {
        Specialty item = ConsoleService.getItemFromConsole("Specialty",controller.getAll());
        if (item != null) {
            System.out.println("editing item = " + item);
            String newName = ConsoleService.getStringParamFromConsole("name");
            if (!newName.equals(item.getName())) {
                item.setName(newName);
                ConsoleService.printItems(controller.update(item));
            }
        }
    }

    private void printItems(Status status) {
        ConsoleService.printItems(controller.getAll(status));
    }

    private void printItemsById() {
        long id = ConsoleService.getIntParamFromConsole("id ");
        ConsoleService.printItems(controller.getById(id));
    }


    private void deleteItem() {
        Specialty item = ConsoleService.getItemFromConsole("Specialty",controller.getAll(Status.ACTIVE));
        if (item != null) {
            System.out.println("deleting item is : " + item);
            controller.delete(item);
        }
    }

    private void unDeleteItem() {
        Specialty item = ConsoleService.getItemFromConsole("Specialty",controller.getAll(Status.DELETED));
        if (item != null) {
            System.out.println("UnDeleting item is : " + item);
            controller.unDelete(item);
        }
    }

    //*****************************************************
}
