package com.example.quarter2.minipeta3;

import java.util.Scanner;

public class ClaimItemFeature {
    public void runFeature(Scanner scanner, SimulatedData data) {
        System.out.println("--- Claim Item ---");
        System.out.print("Post ID to claim: ");
        String idText = scanner.nextLine().trim();
        System.out.println(idText);

        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            System.out.println("That is not TestRunInstruction valid post ID.");
            return;
        }

        Item item = data.findById(id);
        if (item == null) {
            System.out.println("No post with that ID.");
        } else if (item.itemStatus.equals("claim")) {
            System.out.println("This item was already claimed.");
        } else {
            System.out.print("Your name: ");
            String claimant = scanner.nextLine();
            System.out.println(claimant);

            item.itemOwner = claimant;
            item.itemStatus = "claim";
            System.out.println("Post #" + item.postID + " is now claimed by " + claimant + ".");
        }
    }
}
