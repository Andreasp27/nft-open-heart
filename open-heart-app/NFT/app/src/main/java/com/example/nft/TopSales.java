package com.example.nft;

public class TopSales {


    String noitem,itemname, price, growth, image;

    public TopSales(String image, String noitem, String itemname, String price, String growth) {
        this.image = image;
        this.noitem = noitem;
        this.itemname = itemname;
        this.price = price;
        this.growth = growth;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNoitem() {
        return noitem;
    }

    public void setNoitem(String noitem) {
        this.noitem = noitem;
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

    public String getGrowth() {
        return growth;
    }

    public void setGrowth(String growth) {
        this.growth = growth;
    }
}
