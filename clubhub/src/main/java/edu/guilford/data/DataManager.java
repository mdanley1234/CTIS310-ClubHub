package edu.guilford.data;

import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONArray;

import edu.guilford.data.packets.BundlePacket;
import edu.guilford.data.packets.ProfilePacket;
import edu.guilford.supabase.SupabaseAuth;
import edu.guilford.supabase.SupabaseQuery;

/**
 * The DataManager class is responsible for managing data retrieval and storage.
 * All relevant information that is required by the program is stored in this class (except authToken and UUID in SBAuth).
 */
public class DataManager {

    // The static profilePacket variable is populated when a user logs in or signs up (primary user)
    private static ProfilePacket profilePacket;

    // The static dataBundle variable is populated when a user logs in or signs up (dataBundles for the primary user)
    private static ArrayList<DataBundle> dataBundles;

    // Fetch the profilePacket and dataBundles for the user
    public static void initDataManager(UUID profile_id) {
        // profilePacket is populated
        profilePacket = new ProfilePacket(profile_id);

        // dataBundles are populated
        dataBundles = new ArrayList<>();
        JSONArray bundleArray = SupabaseQuery.queryMany("bundles", "profile_id=eq." + SupabaseAuth.getUserId(), "bundle_id");
        for (int i = 0; i < bundleArray.length(); i++) {
            DataBundle dataBundle = new DataBundle(new BundlePacket(UUID.fromString(bundleArray.getJSONObject(i).getString("bundle_id"))));
            dataBundles.add(dataBundle);
        }

    }

    // Data Getters

    public static ProfilePacket getProfilePacket() {
        return profilePacket;
    }

    public static ArrayList<DataBundle> getDataBundles() {
        return dataBundles;
    }
}