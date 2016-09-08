package com.example.mayank.travelagentproject;

/**
 * Created by mayank on 07-09-2016.
 */

public class YourBookingPOJO {

    String userid;
    String agentname;
    String agentaddress;
    String agentcontact;
    String username;
    String useraddress;
    String useremail;
    String usercontact;
    String from;
    String to;
    String date;
    String time;
    String travelpref;
    String cabtime;
    String cartype;
    String carsize;
    String bookingdate;
    String bookingid;

    public YourBookingPOJO(){}

    public YourBookingPOJO(String bookingid,String bookingdate,String agentname, String agentaddress, String agentcontact, String username,
                           String useraddress, String usercontact, String useremail, String from, String to,
                           String date, String time, String travelpref, String cartype, String carsize,
                           String cabtime, String userid) {
        this.bookingid=bookingid;
        this.bookingdate=bookingdate;
        this.agentname = agentname;
        this.agentaddress = agentaddress;
        this.agentcontact = agentcontact;
        this.username = username;
        this.useraddress = useraddress;
        this.usercontact = usercontact;
        this.useremail = useremail;
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.travelpref = travelpref;
        this.cartype = cartype;
        this.carsize = carsize;
        this.cabtime = cabtime;
        this.userid = userid;
    }

    public String getBookingid(){return bookingid;}
    public String getBookingdate(){return bookingdate;}
    public String getAgentaddress() {
        return agentaddress;
    }

    public String getAgentcontact() {
        return agentcontact;
    }

    public String getAgentname() {
        return agentname;
    }

    public String getCabtime() {
        return cabtime;
    }

    public String getCarsize() {
        return carsize;
    }

    public String getCartype() {
        return cartype;
    }

    public String getDate() {
        return date;
    }

    public String getFrom() {
        return from;
    }

    public String getTime() {
        return time;
    }

    public String getTo() {
        return to;
    }

    public String getTravelpref() {
        return travelpref;
    }

    public String getUseraddress() {
        return useraddress;
    }

    public String getUsercontact() {
        return usercontact;
    }

    public String getUseremail() {
        return useremail;
    }

    public String getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }
}
