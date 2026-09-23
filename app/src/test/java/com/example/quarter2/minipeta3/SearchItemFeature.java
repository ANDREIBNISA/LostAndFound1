package com.example.quarter2.minipeta3;

import java.util.Scanner;

public class SearchItemFeature {
    public void runFeature(Scanner scanner, SimulatedData data) {
        System.out.println("--- Search Items ---");
        System.out.print("Keyword: ");
        String keyword = scanner.nextLine().trim();
        System.out.println(keyword);

        int found = 0;
        for (Item item : data.getAll()) {
            if (item.itemName.toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(item);
                found++;
            }
        }
        if (found == 0) {
            System.out.println("No matches.");
        }
    }
}