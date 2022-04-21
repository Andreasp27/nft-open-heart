package com.example.nft;

public class Collected {
    int imageCol;
    String itemname, price, creator;

    public Collected(int imageCol, String itemname, String price, String creator) {
        this.imageCol = imageCol;
        this.itemname = itemname;
        this.price = price;
        this.creator = creator;
    }

    public int getImageCol() {
        return imageCol;
    }

    public void setImageCol(int imageCol) {
        this.imageCol = imageCol;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
