package com.example.quarter2.minipeta3;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class SearchItemFeature {

    public void runFeature(Scanner scanner) {
        ItemDatabase db = new ItemDatabase();

        System.out.print("Search for: ");
        String keyword = scanner.nextLine().trim();
        System.out.println(keyword);

        try {
            ListItemsFeature.printItems(db.searchItems(keyword));
        } catch (IOException | SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
}