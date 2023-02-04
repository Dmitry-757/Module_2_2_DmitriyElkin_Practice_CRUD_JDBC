package com.Dmitry_Elkin.PracticeTaskCRUD.view;



import java.util.Scanner;

public class MainView {
    public static Scanner sc = new Scanner(System.in);

    public void upLevelMenu() {
        boolean exit = false;
        SkillView skillController = new SkillView();
        SpecialtyView specialtyController = new SpecialtyView();
        DeveloperView developerController = new DeveloperView();

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
