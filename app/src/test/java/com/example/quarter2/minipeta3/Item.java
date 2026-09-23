package com.example.quarter2.minipeta3;

public class Item {
    public int postID;
    public String itemName;
    public String itemStatus; // lost / found / claim
    public String itemHolder;
    public String itemOwner;
    public String itemCategory;
    public String itemDescription;
    public String itemLocation;
    public String reportDate;
    public String contactInfo;
    public float matchScore;

    public Item(int postID, String itemName, String itemStatus, String itemCategory,
                String itemDescription, String itemLocation, String reportDate, String contactInfo) {
        this.postID = postID;
        this.itemName = itemName;
        this.itemStatus = itemStatus;
        this.itemCategory = itemCategory;
        this.itemDescription = itemDescription;
        this.itemLocation = itemLocation;
        this.reportDate = reportDate;
        this.contactInfo = contactInfo;
        this.itemHolder = "";
        this.itemOwner = "";
        this.matchScore = 0f;
    }

    @Override
    public String toString() {
        String line = "#" + postID + " [" + itemStatus + "] " + itemName + " (" + itemCategory + ") - "
                + itemDescription + " @ " + itemLocation + " on " + reportDate
                + " | Contact: " + contactInfo;
        if (!itemOwner.isEmpty()) {
            line = line + " | Claimed by: " + itemOwner;
        }
        return line;
    }
}