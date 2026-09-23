package com.example.quarter2.minipeta3;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Test;

public class LostAndFoundFlowTest {

    @Test
    public void fullProgramFlow() {
        StringBuilder input = new StringBuilder();
        int step = 1;
        while (step <= 4) {
            if (step == 1) {
                // main menu: reported items, then add a new item
                input.append("1\n");
                input.append("1\n");
                input.append("Red Umbrella\n");
                input.append("found\n");
                input.append("Accessories\n");
                input.append("Large, wooden handle\n");
                input.append("Canteen\n");
                input.append("0917-000-0004\n");
            } else if (step == 2) {
                // same list: claim the new post (#4)
                input.append("2\n");
                input.append("4\n");
                input.append("Maria Santos\n");
            } else if (step == 3) {
                // back to main menu, then search
                input.append("3\n");
                input.append("2\n");
                input.append("umbrella\n");
            } else {
                // exit
                input.append("3\n");
            }
            step++;
        }

        ByteArrayInputStream in = new ByteArrayInputStream(input.toString().getBytes());
        Scanner scanner = new Scanner(in);

        PrintStream originalOut = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured));

        try {
            new MainMenu().run(scanner);
        } finally {
            System.setOut(originalOut);
        }

        String output = captured.toString();
        System.out.println(output);

        assertTrue(output.contains("Black Wallet"));
        assertTrue(output.contains("Saved as post #4"));
        assertTrue(output.contains("Post #4 is now claimed by Maria Santos."));
        assertTrue(output.contains("Claimed by: Maria Santos"));
        assertTrue(output.contains("Goodbye!"));
    }
}