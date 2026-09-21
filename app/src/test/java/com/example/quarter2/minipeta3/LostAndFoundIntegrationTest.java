package com.example.quarter2.minipeta3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class LostAndFoundIntegrationTest {

    @Test
    public void fullProgramFlow() {
        int itemsToAdd = 3;

        // Step 1: generate the sequence of user choices
        StringBuilder input = new StringBuilder();

        int i = 1;
        while (i <= itemsToAdd) {
            String status;
            String category;
            if (i % 3 == 1) {
                status = "lost";
                category = "Electronics";
            } else if (i % 3 == 2) {
                status = "found";
                category = "Clothing";
            } else {
                status = "claim";
                category = "Documents";
            }

            input.append("1\n");                                  // menu: report an item
            input.append("TEST-Item ").append(i).append("\n");    // ItemName
            input.append(status).append("\n");                    // ItemStatus
            input.append(category).append("\n");                  // ItemCategory
            input.append("Test description ").append(i).append("\n"); // ItemDescription
            input.append("Library\n");                            // ItemLocation

            if (status.equals("found")) {
                input.append("Test Finder\n");                    // ItemHolder
                input.append("\n");                               // ItemOwner (blank)
            } else if (status.equals("lost")) {
                input.append("\n");                               // ItemHolder (blank)
                input.append("Test Owner\n");                     // ItemOwner
            } else {
                input.append("Test Finder\n");
                input.append("Test Owner\n");
            }

            input.append("test").append(i).append("@example.com\n"); // ContactInfo
            i++;
        }

        input.append("2\n");       // menu: list all items
        input.append("3\n");       // menu: search by name
        input.append("TEST-\n");   // the search keyword
        input.append("4\n");       // menu: quit

        // Step 2: turn the text into a stream and give it to the Scanner
        ByteArrayInputStream stream =
                new ByteArrayInputStream(input.toString().getBytes(StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(stream);

        // Step 3: run the whole program while capturing what it prints
        PrintStream originalOut = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured, true));
        try {
            new MainMenu().run(scanner);
        } finally {
            System.setOut(originalOut);
            scanner.close();
        }

        String output = captured.toString();
        System.out.println(output); // show the whole run in the test output

        // Step 4: check the results
        int savedCount = 0;
        int position = output.indexOf("Saved!");
        while (position != -1) {
            savedCount++;
            position = output.indexOf("Saved!", position + 1);
        }

        assertEquals(itemsToAdd, savedCount);
        assertTrue(output.contains("TEST-Item 1"));
        assertTrue(output.contains("TEST-Item " + itemsToAdd));
        assertTrue(output.contains("Goodbye!"));
    }
}
