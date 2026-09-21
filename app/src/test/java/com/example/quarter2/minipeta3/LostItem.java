package com.example.quarter2.minipeta3;

public class LostItem {
    private int PostID;
    private String ItemName;
    private String ItemStatus;      // "lost", "found" or "claimed"
    private String ItemHolder;      // the finder
    private String ItemOwner;       // the owner
    private String ItemCategory;
    private String ItemDescription;
    private String ItemLocation;
    private String ReportDate;
    private String ContactInfo;
    private float MatchScore;

    // Empty form, to be filled in later
    public LostItem() {
    }

    // New report (PostID, ReportDate and MatchScore are handled by the database)
    public LostItem(String ItemName, String ItemStatus, String ItemHolder, String ItemOwner,
                    String ItemCategory, String ItemDescription, String ItemLocation,
                    String ContactInfo) {
        this.ItemName = ItemName;
        this.ItemStatus = ItemStatus;
        this.ItemHolder = ItemHolder;
        this.ItemOwner = ItemOwner;
        this.ItemCategory = ItemCategory;
        this.ItemDescription = ItemDescription;
        this.ItemLocation = ItemLocation;
        this.ContactInfo = ContactInfo;
    }

    public int getPostID() {
        return PostID;
    }

    public void setPostID(int PostID) {
        this.PostID = PostID;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String ItemName) {
        this.ItemName = ItemName;
    }

    public String getItemStatus() {
        return ItemStatus;
    }

    public void setItemStatus(String ItemStatus) {
        this.ItemStatus = ItemStatus;
    }

    public String getItemHolder() {
        return ItemHolder;
    }

    public void setItemHolder(String ItemHolder) {
        this.ItemHolder = ItemHolder;
    }

    public String getItemOwner() {
        return ItemOwner;
    }

    public void setItemOwner(String ItemOwner) {
        this.ItemOwner = ItemOwner;
    }

    public String getItemCategory() {
        return ItemCategory;
    }

    public void setItemCategory(String ItemCategory) {
        this.ItemCategory = ItemCategory;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String ItemDescription) {
        this.ItemDescription = ItemDescription;
    }

    public String getItemLocation() {
        return ItemLocation;
    }

    public void setItemLocation(String ItemLocation) {
        this.ItemLocation = ItemLocation;
    }

    public String getReportDate() {
        return ReportDate;
    }

    public void setReportDate(String ReportDate) {
        this.ReportDate = ReportDate;
    }

    public String getContactInfo() {
        return ContactInfo;
    }

    public void setContactInfo(String ContactInfo) {
        this.ContactInfo = ContactInfo;
    }

    public float getMatchScore() {
        return MatchScore;
    }

    public void setMatchScore(float MatchScore) {
        this.MatchScore = MatchScore;
    }

    @Override
    public String toString() {
        return "LostItem{PostID=" + PostID + ", ItemName='" + ItemName
                + "', ItemStatus='" + ItemStatus + "'}";
    }
}