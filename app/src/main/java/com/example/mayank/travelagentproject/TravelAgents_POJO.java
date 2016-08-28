package com.example.mayank.travelagentproject;

import java.util.ArrayList;

/**
 * Created by UddishVerma on 21/08/16.
 */
public class TravelAgents_POJO {

    public static class TravelAgentsDetails{
        String tAName;
        String tAAddress;
        String tAnumber;

//        int picture;

        public TravelAgentsDetails(String tAName, String tAAddress, String tAnumber) {
            this.tAName = tAName;
            this.tAAddress = tAAddress;
            this.tAnumber = tAnumber;
//            this.picture = picture;
        }
    }

    public static ArrayList<TravelAgentsDetails> getDetails()   {
        ArrayList<TravelAgentsDetails> details = new ArrayList<>();

        details.add(new TravelAgentsDetails("Royal Tours & Travels", "No 5/A, New Bypass Road, Vellore Fort," +
                " Vellore - 632004, Next to GRT Regency", "+(91)-416-2227554"));
        details.add(new TravelAgentsDetails("Jayam Travels", "141A, Netaji nagar, Sathuvacheri, Vellore - 632009," +
                " Gandhi Nagar bus stop", "+(91)-416-2222700"));
        details.add(new TravelAgentsDetails("New Jai Maaruthi Travel", "No 39/5, Gandhi Road, Vellore Bazaar, Vellore" +
                " - 632004, Opposite To CMC Hospital", "+(91)-416-4500460"));
        details.add(new TravelAgentsDetails("Vellore Yathra", "VDM Complex, Katpadi To Vellore Road, Vellore - 632001, " +
                "Opposite To New Bus Stand", "+(91)-416-2256776"));
        details.add(new TravelAgentsDetails("Geethajanli Tours And Travels", "161, gandhi road, Vellore Fort, Vellore -" +
                " 632004, opp to cmc hospital", "+(91)-416-2227491"));
        details.add(new TravelAgentsDetails("Sri Ragavendra Tours & Travels", "No 26, kvs chetty street, Vellore Fort, " +
                "Vellore - 632004, gnanam residency", "+(91)-416-4500076"));
        details.add(new TravelAgentsDetails("Sri Renugambal Travels", "No 1 Gds Building Ground Floor, New Bye Pass Road, Vellore" +
                " Bazaar, Vellore - 632004,Near New Bus Stand", "+(91)-416-3203406"));
        details.add(new TravelAgentsDetails("Howrah Tours & Travels", "45/2_a, gandhi road, Vellore Fort, Vellore - 632004, " +
                "opp to cmc", "+(91)-416-4205886"));
        details.add(new TravelAgentsDetails("Shree Guru Tours & Travels", "D.S.GUEST HOUSE, Mitta Ananda Rao Street, Vellore" +
                " - 632004, Near Gandhi Road ", "+(91)-416-4200065"));
        details.add(new TravelAgentsDetails("M S K Travels", "3/233, Gajaraj Street, Gandhi Nagar, Vellore - 632002",
                "+(91)-416-2241446"));
        return details;

    }
}
