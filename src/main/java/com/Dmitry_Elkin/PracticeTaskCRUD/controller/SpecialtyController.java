package com.Dmitry_Elkin.PracticeTaskCRUD.controller;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.RepositoryFactory;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.SpecialtyRepository;

import static com.Dmitry_Elkin.PracticeTaskCRUD.controller.MainController.sc;
import static com.Dmitry_Elkin.PracticeTaskCRUD.controller.Service.getGenericParamFromConsole;
import static com.Dmitry_Elkin.PracticeTaskCRUD.controller.Service.getStringParamFromConsole;

public class SpecialtyController {

    private static final SpecialtyRepository repository = RepositoryFactory.getSpecialtyRepository();

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
        String name = getStringParamFromConsole("name");
        repository.addOrUpdate(new Specialty(name));
    }

    private void changeItem() {

        Specialty item = getGenericParamFromConsole("Specialty", repository);
        if (item != null) {
//            System.out.println("editing item = " + item.toString());
            System.out.println("editing item = " + item);
            String newName = getStringParamFromConsole("name");
            item.setName(newName);
            repository.addOrUpdate(item);
        }

    }
    private void printItems(Status status) {
        Service.printItems(repository.getAll(status));
    }

    private void printItemsById() {
        long id = Service.getIntParamFromConsole("id ");
        Service.printItems(repository.getById(id));
    }


    private void deleteItem() {
        Specialty item = getGenericParamFromConsole("Specialty", repository, Status.ACTIVE);
        if (item != null) {
//            System.out.println("deleting item is : " + item.toString());
            System.out.println("deleting item is : " + item);
            repository.delete(item);
        }
    }

    private void unDeleteItem() {
        Specialty item = getGenericParamFromConsole("Specialty", repository, Status.DELETED);
        if (item != null) {
//            System.out.println("UnDeleting item is : " + item.toString());
            System.out.println("UnDeleting item is : " + item);
            repository.unDelete(item);
        }
    }

    //*****************************************************
}
