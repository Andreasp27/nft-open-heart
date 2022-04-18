package com.example.nft;

public class Recom {
    String creator, description;
    int imagecrea, imagebann;

    public Recom(String creator, String description, int imagecrea, int imagebann){
        this.creator = creator;
        this.description = description;
        this.imagecrea = imagecrea;
        this.imagebann = imagebann;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImagecrea() {
        return imagecrea;
    }

    public void setImagecrea(int imagecrea) {
        this.imagecrea = imagecrea;
    }

    public int getImagebann() {
        return imagebann;
    }

    public void setImagebann(int imagebann) {
        this.imagebann = imagebann;
    }
}
