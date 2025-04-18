package edu.guilford.data;

import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONArray;

import edu.guilford.data.packets.DataPacket;
import edu.guilford.supabase.SupabaseQuery;

/**
 * A DataBundle contains all the data packets for a specific profile-club bundle
 * Note: The individual data packets are not stored in this class, but pulled dynamically from SB when requested
 */
public class DataBundle {

    // DataBundle Base Components
    private DataPacket bundlePacket; // Contains information about the bundle
    private DataPacket clubPacket;  // Contains information about a select club

    // Builds a dataBundle object from a bundlePacket but not the actual data packets
    public DataBundle(DataPacket bundlePacket) {
        // Fetch data packets
        this.bundlePacket = bundlePacket;
        clubPacket = new DataPacket("clubs", "club_id", bundlePacket.getString("club_id"));
    }

    // Get club name of bundle
    public String getClubName() {
        return clubPacket.getString("club_name");
    }

    // Get bundle id of bundle
    public UUID getBundleId() {
        return UUID.fromString(bundlePacket.getString("bundle_id"));
    }

    // Fetches dataPackets from the SB database based on the user's role in the bundlePacket and returns list of packets
    public ArrayList<DataPacket> fetchPackets() {
        
        // List of data packets to be returned
        ArrayList<DataPacket> dataPackets = new ArrayList<>();

        // Special non-club bundles

        // Dashboard - Special Bundle 0
        if (getClubName().equals("Dashboard")) {

            dataPackets.add(DataManager.getProfilePacket()); // Add profile packet to the dashboard bundle

            // Dashboard add list of clubs
            DataPacket clubList = new DataPacket("list"); // Psuedo packet to hold list of clubs
            for (int i = 0; i < DataManager.getDataBundles().size(); i++) {
                String clubName = DataManager.getDataBundles().get(i).getClubName();
                if (!clubName.equals("Dashboard") && !clubName.equals("Directory")) {
                    clubList.addMetadata(clubName);
                }
            }

            dataPackets.add(clubList); // Add list of clubs to the dashboard bundle

            return dataPackets;
        }

        // Directory - Special Bundle 1
        if (getClubName().equals("Directory")) {

            JSONArray idArray = SupabaseQuery.queryMany("clubs", "", "club_id");
            for (int i = 0; i < idArray.length(); i++) {
                DataPacket packet = new DataPacket("clubs", "club_id", idArray.getJSONObject(i).getString("club_id"));
                if (!packet.get("club_name").equals("Dashboard") && !packet.get("club_name").equals("Directory")) {
                    packet.addMetadata("button"); // Include "button" in metadata for directory to signal a join button
                    dataPackets.add(packet);
                }
            }

            return dataPackets;
        }

        // Select data packets based on the user's role in the bundle
        // NOTE: USE OF CASCADE STATEMENTS
        switch (bundlePacket.getString("role")) {
            case "admin":
                // Build admin level packets

            case "advisor":
                // Build advisor level packets

            case "leadership":
                // Build leadership level packets

            case "member":
                // Build member level packets

                // club table
                dataPackets.add(clubPacket);
        

                // events table
                DataPacket eventPacket = new DataPacket("events", "sponsor_club", clubPacket.getString("club_id"));
                dataPackets.add(eventPacket);

                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + bundlePacket.getString("role"));
        }

        return dataPackets;
    }

    // Helper method for data pulls where bundle_id is primary key
    private DataPacket fetchPacketByBundle(String table) {
        DataPacket dataPacket = new DataPacket(table, "bundle_id", bundlePacket.getString("bundle_id"));
        return dataPacket;
    }

}