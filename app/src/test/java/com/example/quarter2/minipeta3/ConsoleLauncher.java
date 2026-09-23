package com.example.quarter2.minipeta3;

import java.util.Scanner;

public class ConsoleLauncher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        new MainMenu().run(scanner);
        scanner.close();
    }
}
