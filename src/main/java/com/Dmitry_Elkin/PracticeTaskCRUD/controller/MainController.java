package com.Dmitry_Elkin.PracticeTaskCRUD.controller;



import java.util.Scanner;

public class MainController {
    public static Scanner sc = new Scanner(System.in);

    public void upLevelMenu() {
        boolean exit = false;
        SkillController skillController = new SkillController();
        SpecialtyController specialtyController = new SpecialtyController();
        DeveloperController developerController = new DeveloperController();

        while (!exit) {
            System.out.println("1 - work with Skills, " +
                    " 2 - work with Specialties, " +
                    " 3 - work with Developers," +
                    " 0 - exit");
            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> skillController.menu();

                    case 2 -> specialtyController.menu();

                    case 3 -> developerController.menu();

                    case 0 -> exit = true;
                    default -> System.out.println("Wrong input!");
                }
            } else {
                System.out.println("wrong input... Please, use only digits!");
                sc.nextLine();
            }
        }
    }

}
