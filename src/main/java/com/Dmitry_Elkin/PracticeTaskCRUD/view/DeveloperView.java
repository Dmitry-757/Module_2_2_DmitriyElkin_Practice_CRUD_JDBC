package com.Dmitry_Elkin.PracticeTaskCRUD.view;


import com.Dmitry_Elkin.PracticeTaskCRUD.controller.DeveloperController;
import com.Dmitry_Elkin.PracticeTaskCRUD.controller.SkillController;
import com.Dmitry_Elkin.PracticeTaskCRUD.controller.SpecialtyController;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Developer;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;

import java.util.HashSet;

import static com.Dmitry_Elkin.PracticeTaskCRUD.view.MainView.sc;
import static com.Dmitry_Elkin.PracticeTaskCRUD.view.ConsoleService.*;

public class DeveloperView {

//    private static final DeveloperRepository repository = RepositoryFactory.getDeveloperRepository();
    private final DeveloperController controller = new DeveloperController();
    private final SkillController skillController = new SkillController();
    private final SpecialtyController specialtyController = new SpecialtyController();

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
        String firstName = getStringParamFromConsole("first name");
        String lastName = getStringParamFromConsole("second name");

        HashSet<Skill> skills = new HashSet<>(getGenericListFromConsole("Skill", skillController.getAll()));
        Specialty specialty = getItemFromConsole("Specialty", specialtyController.getAll());

        ConsoleService.printItems(controller.insert(new Developer(firstName, lastName, skills, specialty)));
    }



    private void changeItem() {
        Developer item = ConsoleService.getItemFromConsole("Developer",controller.getAll());
        if (item != null) {
            System.out.println("editing item = " + item);
            String firstName = getStringParamFromConsole("first name");
            String lastName = getStringParamFromConsole("second name");
            HashSet<Skill> skills = new HashSet<>(getGenericListFromConsole("Skills", skillController.getAll()));
            Specialty specialty = getItemFromConsole("Specialty", specialtyController.getAll());
            item.setFirstName(firstName);
            item.setLastName(lastName);
            if (skills.size() != 0) {
                item.setSkills(skills);
            }
            if (specialty != null) {
                item.setSpecialty(specialty);
            }
            ConsoleService.printItems(controller.update(item));
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
        Developer item = ConsoleService.getItemFromConsole("Developer",controller.getAll(Status.ACTIVE));
        if (item != null) {
            System.out.println("deleting item is : " + item);
            controller.delete(item);
        }

    }

    private void unDeleteItem() {
        Developer item = ConsoleService.getItemFromConsole("Developer",controller.getAll(Status.DELETED));
        if (item != null) {
            System.out.println("UnDeleting item is : " + item);
            controller.unDelete(item);
        }
    }
}
