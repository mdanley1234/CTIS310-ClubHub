package edu.guilford.data;

import java.util.ArrayList;
import java.util.UUID;

import edu.guilford.data.packets.BundlePacket;
import edu.guilford.data.packets.ClubPacket;
import edu.guilford.data.packets.DataPacket;

/**
 * A ContentBundle contains a list of Data objects that each correspond to a
 * content panel.
 */
public class DataBundle {

    // Non-special data packets

    // DataBundle DataPacket Components
    // Member
    private DataPacket attendancePacket;
    private ArrayList<DataPacket> messagePackets;
    private ArrayList<DataPacket> eventPackets;
    private ArrayList<DataPacket> pollPackets;

    // Leadership

    //Advisor

    // Admin

    // DataBundle Base Components
    private BundlePacket bundlePacket; // Contains information about the bundle
    private ClubPacket clubPacket;  // Contains information about a select club (or Dashboard, or Directory)

    // Builds a dataBundle object from a bundle_id by fetching a bundlePacket, clubPacket, and userRole
    // The userRole enum determines which data packets are fetched from the data base
    public DataBundle(BundlePacket bundlePacket) {
        // Fetch data packets
        this.bundlePacket = bundlePacket;
        clubPacket = new ClubPacket(UUID.fromString(bundlePacket.getString("club_id")));

        // TODO: Data Testing
        System.out.println("Bundle Packet: " + bundlePacket.toString());
        System.out.println("Club Packet: " + clubPacket.toString());
    }

    // Fetches dataPackets from the SB database based on userRole and store locally. Return true if successful.
    public boolean buildBundle() {
        // Extract userRole enum
        switch (bundlePacket.getString("role")) {
            case "admin":
                // Build admin level packets
            case "advisor":
                // Build advisor level packets
            case "leadership":
                // Build leadership level packets
            case "member":
                // Build member level packets            
                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + bundlePacket.getString("role"));
        }
    }

}
