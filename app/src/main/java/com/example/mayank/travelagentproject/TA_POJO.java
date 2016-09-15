package com.example.mayank.travelagentproject;

/**
 * Created by mayank on 15-09-2016.
 */
public class TA_POJO {
    String name;
    String email;
    String number;
    String location;
    String price;
    String imgoneurl;
    String imgtwourl;

    public TA_POJO(){}

    public TA_POJO(String name, String location, String number, String email, String price,String imgoneurl,String imgtwourl) {
        this.name = name;
        this.location = location;
        this.number = number;
        this.email = email;
        this.price = price;
        this.imgoneurl=imgoneurl;
        this.imgtwourl=imgtwourl;
    }

    public String getImgoneurl() {
        return imgoneurl;
    }

    public String getImgtwourl() {
        return imgtwourl;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getPrice() {
        return price;
    }
}
