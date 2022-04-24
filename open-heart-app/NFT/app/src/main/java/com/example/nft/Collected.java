package com.example.nft;

public class Collected {
    String imageCol, itemname, price, creator;
    int id;

    public Collected(String imageCol, String itemname, String price, String creator, int id) {
        this.imageCol = imageCol;
        this.itemname = itemname;
        this.price = price;
        this.creator = creator;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageCol() {
        return imageCol;
    }

    public void setImageCol(String imageCol) {
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
