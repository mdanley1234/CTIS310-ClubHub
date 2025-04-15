package edu.guilford.data;

import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONArray;

import edu.guilford.data.packets.ClubPacket;
import edu.guilford.data.packets.DataPacket;
import edu.guilford.supabase.SupabaseQuery;

/**
 * A ContentBundle contains a list of Data objects that each correspond to a
 * content panel.
 */
public class DataBundle {

    // DataBundle Base Components
    private DataPacket bundlePacket; // Contains information about the bundle
    private DataPacket clubPacket;  // Contains information about a select club (or Dashboard, or Directory)

    // Builds a dataBundle object from a bundle_id by fetching a bundlePacket, clubPacket, and userRole
    // The userRole enum determines which data packets are fetched from the data base
    public DataBundle(DataPacket bundlePacket) {
        // Fetch data packets
        this.bundlePacket = bundlePacket;
        clubPacket = new DataPacket("clubs", "club_id", bundlePacket.getString("club_id"));
    }

    // Get club name of bundle
    public String getClubName() {
        return clubPacket.getString("club_name");
    }

    // Fetches dataPackets from the SB database based on userRole and store locally. Return true if successful.
    public ArrayList<DataPacket> fetchPackets() {
        
        ArrayList<DataPacket> dataPackets = new ArrayList<>();

        //SPECIAL CASES
        if (getClubName().equals("Dashboard")) {



            return dataPackets;
        }
        else if (getClubName().equals("Directory")) {

            JSONArray idArray = SupabaseQuery.queryMany("clubs", "", "club_id");
            for (int i = 0; i < idArray.length(); i++) {
                dataPackets.add(new ClubPacket(UUID.fromString(idArray.getJSONObject(i).getString("club_id"))));
            }

            return dataPackets;
        }

        // Extract usDerRole enum

        switch (bundlePacket.getString("role")) {
            case "admin":
                // Build admin level packets
            case "advisor":
                // Build advisor level packets
            case "leadership":
                // Build leadership level packets
            case "member":

                // attendance
                dataPackets.add(fetchPacketByBundle("attendance"));
            
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + bundlePacket.getString("role"));
        }

        return dataPackets;
    }

    private DataPacket fetchPacketByBundle(String table) {
        DataPacket dataPacket = new DataPacket(table, "bundle_id", bundlePacket.getString("bundle_id"));
        return dataPacket;
    }

}
