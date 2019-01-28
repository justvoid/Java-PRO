package net.ukr.just_void;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class Main {
    private static EntityManagerFactory emf;
    private static DishesController dc;

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("Lesson06_HW_2_1");
        dc = new DishesController(emf);
        try {
            menu();
        } finally {
            emf.close();
        }
    }

    private static void menu() {
        Scanner sc = new Scanner(System.in);
        String input;
        do {
            System.out.println("======= Main Menu ======");
            System.out.println("\t1. Generate random dishes");
            System.out.println("\t2. Add a dish");
            System.out.println("\t3. List all dishes");
            System.out.println("\t4. List dishes by price");
            System.out.println("\t5. List dishes with a discount");
            System.out.println("\t6. Get a set of dishes weighting less than 1kg");
            System.out.println("\t7. Exit");
            input = sc.nextLine();
            switch (input) {
                case "1": {
                    dc.generateMenu();
                    break;
                }
                case "2": {
                    dc.manualAddDish();
                    break;
                }
                case "3": {
                    dc.selectAndShowMenu("SELECT d FROM Dish d", "Menu");
                    break;
                }
                case "4": {
                    dc.selectByPrice();
                    break;
                }
                case "5": {
                    dc.selectAndShowMenu("SELECT d FROM Dish d WHERE d.discount = true", "Dishes with discounts");
                    break;
                }
                case "6": {
                    dc.showDishSetWeightLimit(1000);
                    break;
                }
            }
        } while (!input.equals("7"));
    }
}