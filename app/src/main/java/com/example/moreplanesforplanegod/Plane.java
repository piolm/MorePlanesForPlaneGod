package com.example.moreplanesforplanegod;

public class Plane {

    private String video_link;
    private String image_src;
    private String name;
    private String description;
    private int enginesAmount;
    private int planeLength;
    private int crew;
    private float wingSize;
    private float wingSize1;

    public Plane() {
    }

    public Plane(String video_link, String image_src, String name, String description, int enginesAmount, int planeLength, int crew, float wingSize, float wingSize1) {
        this.video_link = video_link;
        this.image_src = image_src;
        this.name = name;
        this.description = description;
        this.enginesAmount = enginesAmount;
        this.planeLength = planeLength;
        this.crew = crew;
        this.wingSize = wingSize;
        this.wingSize1 = wingSize1;
    }

    public String getImage_src() {
        return image_src;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEnginesAmount() {
        return enginesAmount;
    }

    public void setEnginesAmount(int enginesAmount) {
        this.enginesAmount = enginesAmount;
    }

    public int getPlaneLength() {
        return planeLength;
    }

    public void setPlaneLength(int planeLength) {
        this.planeLength = planeLength;
    }

    public int getCrew() {
        return crew;
    }

    public void setCrew(int crew) {
        this.crew = crew;
    }

    public float getWingSize() {
        return wingSize;
    }

    public void setWingSize(float wingSize) {
        this.wingSize = wingSize;
    }

    public float getWingSize1() {
        return wingSize1;
    }

    public void setWingSize1(float wingSize1) {
        this.wingSize1 = wingSize1;
    }
}
