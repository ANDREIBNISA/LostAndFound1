package com.example.quarter2.minipeta3;

import java.util.Scanner;

public class ConsoleApp {

    public static void main(String[] args) {
        // The one and only Scanner on the keyboard. Everything else receives it.
        Scanner scanner = new Scanner(System.in);
        new MainMenu().run(scanner);
        scanner.close();
    }
}
