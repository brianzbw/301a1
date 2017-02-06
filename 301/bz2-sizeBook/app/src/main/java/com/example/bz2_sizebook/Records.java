package com.example.bz2_sizebook;


/**
 * Created by Bowen on 2017/2/5.
 * the data class, save name (textual)
 * date (when the dimensions were reasonably valid, in yyyy-mm-dd format)
 * neck (circumference in inches, numeric)
 * bust (circumference in inches, numeric)
 * chest (circumference in inches, numeric)
 * waist (circumference in inches, numeric)
 * hip (circumference in inches, numeric)
 * inseam (length in inches, numeric)
 * comment (textual)
 */

public class Records {
    private String name;
    private String date;
    private Integer neck; //(circumference in inches, numeric)
    private Integer bust; //(circumference in inches, numeric)
    private Integer chest; //(circumference in inches, numeric)
    private Integer waist; // circumference in inches, numeric)
    private Integer hip;//(circumference in inches, numeric)
    private Integer inseam; //(length in inches, numeric)
    private String comment; //(textual)

    public Records(String name) {this.name = name;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getNeck() {
        return neck;
    }

    public void setNeck(Integer neck) {
        this.neck = neck;
    }

    public Integer getBust() {
        return bust;
    }

    public void setBust(Integer bust) {
        this.bust = bust;
    }

    public Integer getChest() {
        return chest;
    }

    public void setChest(Integer chest) {
        this.chest = chest;
    }

    public Integer getWaist() {
        return waist;
    }

    public void setWaist(Integer waist) {
        this.waist = waist;
    }

    public Integer getHip() {
        return hip;
    }

    public void setHip(Integer hip) {
        this.hip = hip;
    }

    public Integer getInseam() {
        return inseam;
    }

    public void setInseam(Integer inseam) {
        this.inseam = inseam;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

