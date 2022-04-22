package com.example.nft;

public class Created {
    String itemname, price, owned;
    int image;

    public Created(String itemname, String price, String owned, int image) {
        this.itemname = itemname;
        this.price = price;
        this.owned = owned;
        this.image = image;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemane) {
        this.itemname = itemname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOwned() {
        return owned;
    }

    public void setOwned(String owned) {
        this.owned = owned;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
