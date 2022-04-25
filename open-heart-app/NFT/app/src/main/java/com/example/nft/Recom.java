package com.example.nft;

public class Recom {
    String creator, description, imagecrea, imagebann;
    int id;

    public Recom(String creator, String description, String imagecrea, String imagebann, int id){
        this.creator = creator;
        this.description = description;
        this.imagecrea = imagecrea;
        this.imagebann = imagebann;
        this.id = id;
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

    public String getImagecrea() {
        return imagecrea;
    }

    public void setImagecrea(String imagecrea) {
        this.imagecrea = imagecrea;
    }

    public String getImagebann() {
        return imagebann;
    }

    public void setImagebann(String imagebann) {
        this.imagebann = imagebann;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
