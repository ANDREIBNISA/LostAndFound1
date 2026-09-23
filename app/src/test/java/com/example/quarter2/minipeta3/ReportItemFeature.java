package com.example.quarter2.minipeta3;

import java.util.Scanner;

public class ReportItemFeature {
    public void runFeature(Scanner scanner, SimulatedData data) {
        System.out.println("--- Report Item ---");

        System.out.print("Item name: ");
        String name = scanner.nextLine();
        System.out.println(name);

        System.out.print("Status (lost/found): ");
        String status = scanner.nextLine();
        System.out.println(status);

        System.out.print("Category: ");
        String category = scanner.nextLine();
        System.out.println(category);

        System.out.print("Description: ");
        String desc = scanner.nextLine();
        System.out.println(desc);

        System.out.print("Location: ");
        String location = scanner.nextLine();
        System.out.println(location);

        System.out.print("Contact info: ");
        String contact = scanner.nextLine();
        System.out.println(contact);

        int id = data.add(name, status, category, desc, location, "2026-09-23", contact);
        System.out.println("Saved as post #" + id);
    }
}
