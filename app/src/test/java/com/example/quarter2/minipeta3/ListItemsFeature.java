package com.example.quarter2.minipeta3;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ListItemsFeature {

    public void runFeature(Scanner scanner) {
        ItemDatabase db = new ItemDatabase();

        try {
            printItems(db.getAllItems());
        } catch (IOException | SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    // Also used by the search feature
    public static void printItems(List<LostItem> items) {
        if (items.isEmpty()) {
            System.out.println("No items found.");
            return;
        }
        for (LostItem item : items) {
            System.out.println("#" + item.getPostID() + " | " + item.getItemName()
                    + " | " + item.getItemStatus()
                    + " | " + item.getItemCategory()
                    + " | " + item.getItemLocation()
                    + " | holder: " + item.getItemHolder()
                    + " | owner: " + item.getItemOwner()
                    + " | " + item.getReportDate()
                    + " | " + item.getContactInfo());
        }
    }
}