package com.example.quarter2.minipeta3;

import java.util.Scanner;

public class MainMenu {

    public void run(Scanner scanner) {
        ReportItemFeature reportFeature = new ReportItemFeature();
        ListItemsFeature listFeature = new ListItemsFeature();
        SearchItemFeature searchFeature = new SearchItemFeature();

        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("=== Lost and Found ===");
            System.out.println("1. Report an item");
            System.out.println("2. List all items");
            System.out.println("3. Search by name");
            System.out.println("4. Quit");
            System.out.print("Choose: ");

            if (!scanner.hasNextLine()) {
                System.out.println("(no more input)");
                break;
            }
            String choice = scanner.nextLine().trim();
            System.out.println(choice);

            switch (choice) {
                case "1":
                    reportFeature.runFeature(scanner);
                    break;
                case "2":
                    listFeature.runFeature(scanner);
                    break;
                case "3":
                    searchFeature.runFeature(scanner);
                    break;
                case "4":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Please type 1, 2, 3, or 4.");
            }
        }
    }
}
