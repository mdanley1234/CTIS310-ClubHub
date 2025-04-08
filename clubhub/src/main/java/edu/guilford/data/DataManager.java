package edu.guilford.data;

import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONArray;

import edu.guilford.data.packets.BundlePacket;
import edu.guilford.data.packets.ClubPacket;
import edu.guilford.data.packets.ProfilePacket;
import edu.guilford.supabase.SupabaseAuth;
import edu.guilford.supabase.SupabaseQuery;

/**
 * The DataManager class is responsible for managing data retrieval and storage.
 * All relevant information that is required by the program is stored in this class (except authToken and UUID in SBAuth).
 */
public class DataManager {
    
    // DATA RETRIEVAL AND STORAGE OBJECTS

    // The static profilePacket variable is populated when a user logs in or signs up (primary user)
    private static ProfilePacket profilePacket;

    // The static clubPackets variable is populated when a user logs in or signs up (all clubs)
    private static ArrayList<ClubPacket> clubPackets;

    // The static bundlePackets variable is populated when a user logs in or signs up (all bundles for the user)
    private static ArrayList<BundlePacket> bundlePackets;

    // Fetch the profilePacket for the logged-in user, clubPackets for all clubs, and bundlePackets for all bundles for the user
    public static void initDataManager(UUID profile_id) {
        // profilePacket is populated
        profilePacket = new ProfilePacket(profile_id);

        // Public clubPackets are populated
        clubPackets = new ArrayList<>();
        JSONArray idArray = SupabaseQuery.queryMany("clubs", "", "club_id");
        for (int i = 0; i < idArray.length(); i++) {
            clubPackets.add(new ClubPacket(UUID.fromString(idArray.getJSONObject(i).getString("club_id"))));
        }

        // Fetch all bundle UUIDs for the logged-in user
        bundlePackets = new ArrayList<>();
        JSONArray bundleArray = SupabaseQuery.queryMany("bundles", "profile_id=eq." + SupabaseAuth.getUserId(), "bundle_id");
        for (int i = 0; i < bundleArray.length(); i++) {
            bundlePackets.add(new BundlePacket(UUID.fromString(bundleArray.getJSONObject(i).getString("bundle_id"))));
        }

        // TODO: TESTING
        System.out.println("Profile Packet: " + profilePacket);

        System.out.println("Club Packets:");
        for (ClubPacket clubPacket : clubPackets) {
            System.out.println(clubPacket);
        }

        System.out.println("Bundle Packets:");
        for (BundlePacket bundlePacket : bundlePackets) {
            System.out.println(bundlePacket);
        }

    }

    public static ProfilePacket getProfilePacket() {
        return profilePacket;
    }

    public static ArrayList<ClubPacket> getClubPackets() {
        return clubPackets;
    }

    public static ArrayList<BundlePacket> getBundlePackets() {
        return bundlePackets;
    }






}
