package com.example.devendra.sharing;

/**
 * Created by Devendra on 3/23/2017.
 */

public class Items {

    int itemId;
    int userId;
    String name , category , description ;
    byte [] image;
    float rateItems;

    public float getRateItems() {
        return rateItems;
    }

    public void setRateItems(float rateItems) {
        this.rateItems = rateItems;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getItemId() {
        return itemId;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
