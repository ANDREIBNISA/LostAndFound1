package com.example.quarter2.minipeta3;

import java.util.ArrayList;
import java.util.List;

public class SimulatedData {
    private final List<Item> items = new ArrayList<>();
    private int nextId = 1;

    public SimulatedData() {
        add("Black Wallet", "lost", "Accessories", "Leather, has student ID", "Library", "2026-09-15", "0917-000-0001");
        add("Blue Water Bottle", "found", "Personal", "Metal, dented cap", "Gym", "2026-09-16", "0917-000-0002");
        add("Scientific Calculator", "lost", "Electronics", "Casio fx-991", "Room 204", "2026-09-17", "0917-000-0003");
    }

    public int add(String name, String status, String category, String desc,
                   String location, String date, String contact) {
        items.add(new Item(nextId, name, status, category, desc, location, date, contact));
        return nextId++;
    }

    public List<Item> getAll() {
        return items;
    }

    public Item findById(int id) {
        for (Item item : items) {
            if (item.postID == id) {
                return item;
            }
        }
        return null;
    }
}