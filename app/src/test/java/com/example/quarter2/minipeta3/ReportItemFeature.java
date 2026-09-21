package com.example.quarter2.minipeta3;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class ReportItemFeature {

    public void runFeature(Scanner scanner) {
        ItemDatabase db = new ItemDatabase();

        System.out.print("ItemName: ");
        String itemName = scanner.nextLine().trim();
        System.out.println(itemName);

        String itemStatus = "";
        while (!itemStatus.equals("lost") && !itemStatus.equals("found")
                && !itemStatus.equals("claim")) {
            System.out.print("ItemStatus (lost/found/claim): ");
            itemStatus = scanner.nextLine().trim().toLowerCase();
            System.out.println(itemStatus);
        }

        System.out.print("ItemCategory: ");
        String itemCategory = scanner.nextLine().trim();
        System.out.println(itemCategory);

        System.out.print("ItemDescription: ");
        String itemDescription = scanner.nextLine().trim();
        System.out.println(itemDescription);

        System.out.print("ItemLocation: ");
        String itemLocation = scanner.nextLine().trim();
        System.out.println(itemLocation);

        System.out.print("ItemHolder (the finder, or leave blank): ");
        String itemHolder = scanner.nextLine().trim();
        System.out.println(itemHolder);

        System.out.print("ItemOwner (the owner, or leave blank): ");
        String itemOwner = scanner.nextLine().trim();
        System.out.println(itemOwner);

        System.out.print("ContactInfo: ");
        String contactInfo = scanner.nextLine().trim();
        System.out.println(contactInfo);

        try {
            db.addItem(new LostItem(itemName, itemStatus, itemHolder, itemOwner,
                    itemCategory, itemDescription, itemLocation, contactInfo));
            System.out.println("Saved!");
        } catch (IOException | SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
}

