package com.example.quarter2.minipeta3;

import java.util.Scanner;

public class MainMenu {
    private final SimulatedData data = new SimulatedData();

    public void run(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("=== Lost and Found ===");
            System.out.println("1. Reported items");
            System.out.println("2. Search items");
            System.out.println("3. Exit");
            System.out.print("Choice: ");

            int choice = Integer.parseInt(scanner.nextLine().trim());
            System.out.println(choice);

            if (choice == 1) {
                new ViewItemsFeature().runFeature(scanner, data);
            } else if (choice == 2) {
                new SearchItemFeature().runFeature(scanner, data);
            } else if (choice == 3) {
                System.out.println("Goodbye!");
                running = false;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
}
