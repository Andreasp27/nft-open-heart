package com.example.nft;

public class Trend {
    String creator, price, itemname, image;
    int id;

    public Trend (String itemname, String price, String creator, String image, int id){

        this.creator = creator;
        this.itemname = itemname;
        this.price = price;
        this.image = image;
        this.id = id;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
