package com.example.quarter2.minipeta3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ItemDatabase {

    // Opens a connection using the login details in config.properties.
    // It checks a few likely places because the IDE can run from different folders.
    private Connection connect() throws IOException, SQLException {
        String[] places = {"config.properties", "../config.properties", "app/config.properties"};
        File configFile = null;
        for (String place : places) {
            File f = new File(place);
            if (f.exists()) {
                configFile = f;
                break;
            }
        }
        if (configFile == null) {
            throw new IOException("Could not find config.properties in the project's top folder.");
        }

        Properties config = new Properties();
        try (FileInputStream in = new FileInputStream(configFile)) {
            config.load(in);
        }

        return DriverManager.getConnection(
                config.getProperty("db.url"),
                config.getProperty("db.user"),
                config.getProperty("db.password"));
    }

    // Saves one report as a new row
    public void addItem(LostItem item) throws IOException, SQLException {
        String sql = "INSERT INTO lost_items (item_name, item_status, item_holder, item_owner, "
                + "item_category, item_description, item_location, contact_info) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getItemName());
            ps.setString(2, item.getItemStatus());
            ps.setString(3, item.getItemHolder());
            ps.setString(4, item.getItemOwner());
            ps.setString(5, item.getItemCategory());
            ps.setString(6, item.getItemDescription());
            ps.setString(7, item.getItemLocation());
            ps.setString(8, item.getContactInfo());
            ps.executeUpdate();
        }
    }

    // Finds items whose name contains the keyword
    public List<LostItem> searchItems(String keyword) throws IOException, SQLException {
        String sql = "SELECT post_id, item_name, item_status, item_holder, item_owner, "
                + "item_category, item_description, item_location, report_date, "
                + "contact_info, match_score "
                + "FROM lost_items WHERE item_name ILIKE ? ORDER BY post_id";
        List<LostItem> results = new ArrayList<>();

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(readItem(rs));
                }
            }
        }
        return results;
    }

    // Gets every item (an empty keyword matches everything)
    public List<LostItem> getAllItems() throws IOException, SQLException {
        return searchItems("");
    }

    // Turns one database row into a LostItem
    private LostItem readItem(ResultSet rs) throws SQLException {
        LostItem item = new LostItem();
        item.setPostID(rs.getInt("post_id"));
        item.setItemName(rs.getString("item_name"));
        item.setItemStatus(rs.getString("item_status"));
        item.setItemHolder(rs.getString("item_holder"));
        item.setItemOwner(rs.getString("item_owner"));
        item.setItemCategory(rs.getString("item_category"));
        item.setItemDescription(rs.getString("item_description"));
        item.setItemLocation(rs.getString("item_location"));
        item.setReportDate(rs.getString("report_date"));
        item.setContactInfo(rs.getString("contact_info"));
        item.setMatchScore(rs.getFloat("match_score"));
        return item;
    }
}