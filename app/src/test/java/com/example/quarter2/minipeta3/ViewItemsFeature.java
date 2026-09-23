package com.example.quarter2.minipeta3;

import java.util.Scanner;

public class ViewItemsFeature {
    public void runFeature(Scanner scanner, SimulatedData data) {
        boolean back = false;
        while (!back) {
            System.out.println("--- Reported Items ---");
            for (Item item : data.getAll()) {
                System.out.println(item);
            }
            System.out.println("1. Add new item");
            System.out.println("2. Claim an item");
            System.out.println("3. Back");
            System.out.print("Choice: ");

            int choice = Integer.parseInt(scanner.nextLine().trim());
            System.out.println(choice);

            if (choice == 1) {
                new ReportItemFeature().runFeature(scanner, data);
            } else if (choice == 2) {
                new ClaimItemFeature().runFeature(scanner, data);
            } else if (choice == 3) {
                back = true;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
}