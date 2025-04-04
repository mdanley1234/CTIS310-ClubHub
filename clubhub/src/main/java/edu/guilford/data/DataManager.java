package edu.guilford.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.guilford.supabase.SupabaseQuery;

/**
 * The DataManager class is responsible for managing the data retrieval and storage
 * All relevant information that is required by the program is stored in this class (except SBAuth)
 * and updated as needed.
 */
public class DataManager {
    
    // DATA RETRIEVAL AND STORAGE OBJECTS

    // The static profilePacket variable is populated when a user logs in or signs up (primary user)
    private static ProfilePacket profilePacket;

    // The static clubPackets variable is populated when a user logs in or signs up (all clubs)
    private static ArrayList<ClubPacket> clubPackets;

    // Populate clubPackets with all clubs (user associations are stored in bundles)
    public static void buildClubList() {
        clubPackets = new ArrayList<>();

        JSONArray clubArray = SupabaseQuery.queryMany("clubs", "", "");

        for (int i = 0; i < clubArray.length(); i++) {
            JSONObject clubObject = clubArray.getJSONObject(i);
            ClubPacket clubPacket = new ClubPacket(
                clubObject.getString("club_name"),
                clubObject.getInt("founding_year"),
                clubObject.getString("club_description"),
                clubObject.getString("club_website"),
                clubObject.getString("meeting_schedule"),
                clubObject.getString("meeting_location"),
                clubObject.getString("club_email"),
                clubObject.getBoolean("active")
            );
            clubPackets.add(clubPacket);
        }
    }













    // Getters and setters

    public static ProfilePacket getProfilePacket() {
        return profilePacket;
    }

    public static void setProfilePacket(ProfilePacket profilePacket) {
        DataManager.profilePacket = profilePacket;
    }



}
