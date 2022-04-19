package com.example.nft;

public class Trend {
    String creator, price, itemname;
    int image;

    public Trend (String itemname, String price, String creator, int image){
        this.creator = creator;
        this.itemname = itemname;
        this.price = price;
        this.image = image;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}