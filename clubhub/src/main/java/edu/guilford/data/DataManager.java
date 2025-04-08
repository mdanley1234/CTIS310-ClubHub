package edu.guilford.data;

import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONArray;

import edu.guilford.data.packets.ClubPacket;
import edu.guilford.data.packets.ProfilePacket;
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

    public static void initDataManager(UUID profile_id) {
        profilePacket = new ProfilePacket(profile_id);

        clubPackets = new ArrayList<>();

        JSONArray idArray = SupabaseQuery.queryMany("clubs", "", "club_id");
        for (int i = 0; i < idArray.length(); i++) {
            clubPackets.add(new ClubPacket(UUID.fromString(idArray.getJSONObject(i).getString("club_id"))));
        }
    }




}
