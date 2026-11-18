package com.example.lostandfound;

/**
 * Picks the placeholder photo that goes with an item name. The photos are in res/drawable-nodpi
 * (downloaded from Wikimedia Commons, see PHOTO_CREDITS.md). When the database arrives, each
 * report will carry its own photo instead.
 */
final class ItemPictures {

    private ItemPictures() {
    }

    /** The drawable for this item name, for example "Blue umbrella" gives the umbrella photo. */
    static int forName(String itemName) {
        String name = itemName == null ? "" : itemName.toLowerCase();

        // The order matters: "Laptop charger" must give the charger, not the laptop.
        if (name.contains("wallet")) return R.drawable.photo_wallet;
        if (name.contains("cash")) return R.drawable.photo_cash;
        if (name.contains("umbrella")) return R.drawable.photo_umbrella;
        if (name.contains("bottle")) return R.drawable.photo_bottle;
        if (name.contains("key")) return R.drawable.photo_keys;
        if (name.contains("calculator")) return R.drawable.photo_calculator;
        if (name.contains("airpods") || name.contains("earbuds")) return R.drawable.photo_earbuds;
        if (name.contains("headphones")) return R.drawable.photo_headphones;
        if (name.contains("hoodie") || name.contains("jacket")) return R.drawable.photo_hoodie;
        if (name.contains("notebook")) return R.drawable.photo_notebook;
        if (name.contains("charger")) return R.drawable.photo_charger;
        if (name.contains("glasses")) return R.drawable.photo_glasses;
        if (name.contains("lunchbox")) return R.drawable.photo_lunchbox;
        if (name.contains("pencil case")) return R.drawable.photo_pencilcase;
        if (name.contains("backpack")) return R.drawable.photo_backpack;
        if (name.contains("watch")) return R.drawable.photo_watch;
        if (name.contains("laptop")) return R.drawable.photo_laptop;
        if (name.contains("flash drive")) return R.drawable.photo_flashdrive;
        if (name.contains("mouse")) return R.drawable.photo_mouse;
        if (name.matches(".*\\bcap\\b.*")) return R.drawable.photo_cap;
        if (name.contains("sneakers") || name.contains("shoes")) return R.drawable.photo_sneakers;
        if (name.contains("textbook") || name.matches(".*\\bbook\\b.*")) return R.drawable.photo_book;
        if (name.contains("ipair") || name.contains("phone")) return R.drawable.photo_phone;
        if (name.contains("rtx")) return R.drawable.photo_gpu;
        if (name.contains("ryzen")) return R.drawable.photo_cpu;
        if (name.matches(".*\\bram\\b.*")) return R.drawable.photo_ram;
        if (name.contains("card")) return R.drawable.pic_card;

        return R.drawable.ic_picture_placeholder;
    }
}
