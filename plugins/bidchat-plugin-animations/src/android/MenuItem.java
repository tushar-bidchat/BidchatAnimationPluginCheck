package com.bidchat.BidchatAnimations;

/**
 * Created by Tushar
 * Created on 12/26/16.
 */

public class MenuItem {

    private String name;
    private int imageId;

    public MenuItem(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
