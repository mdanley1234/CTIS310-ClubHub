package edu.guilford.data;

import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONArray;

import edu.guilford.data.packets.DataPacket;
import edu.guilford.supabase.SupabaseQuery;

/**
 * {@code DataBundle} represents a container for all data packets related to
 * a user's membership in a specific club (or a special area like Dashboard or Directory).
 * <p>
 * It fetches necessary data dynamically from the Supabase backend and packages
 * them into {@link DataPacket}s depending on the role and context.
 */
public class DataBundle {

    /** The data packet containing bundle information, such as bundle_id and role */
    private DataPacket bundlePacket;

    /** The data packet containing information about the associated club */
    private DataPacket clubPacket;

    /**
     * Constructs a new {@code DataBundle} from an existing bundle packet.
     * It automatically fetches the corresponding club packet using the bundle's club_id.
     *
     * @param bundlePacket the packet containing metadata about the user's membership
     */
    public DataBundle(DataPacket bundlePacket) {
        this.bundlePacket = bundlePacket;
        this.clubPacket = new DataPacket("clubs", "club_id", bundlePacket.getString("club_id"));
    }

    /**
     * Returns the name of the club associated with this bundle.
     *
     * @return the club name
     */
    public String getClubName() {
        return clubPacket.getString("club_name");
    }

    /**
     * Returns the UUID of this bundle.
     *
     * @return the bundle UUID
     */
    public UUID getBundleId() {
        return UUID.fromString(bundlePacket.getString("bundle_id"));
    }

    /**
     * Dynamically fetches and returns the relevant data packets from Supabase
     * based on the club type or user role.
     *
     * @return a list of {@link DataPacket} objects for the bundle
     */
    public ArrayList<DataPacket> fetchPackets() {
        ArrayList<DataPacket> dataPackets = new ArrayList<>();

        // Special case: Dashboard (home page)
        if (getClubName().equals("Dashboard")) {
            dataPackets.add(DataManager.getProfilePacket());

            // Create pseudo-packet to list club names the user belongs to
            DataPacket clubList = new DataPacket("list");
            for (DataBundle bundle : DataManager.getDataBundles()) {
                String name = bundle.getClubName();
                if (!name.equals("Dashboard") && !name.equals("Directory")) {
                    clubList.addMetadata(name);
                }
            }

            dataPackets.add(clubList);
            return dataPackets;
        }

        // Special case: Directory (joinable clubs list)
        if (getClubName().equals("Directory")) {
            JSONArray idArray = SupabaseQuery.queryMany("clubs", "", "club_id");

            for (int i = 0; i < idArray.length(); i++) {
                DataPacket packet = new DataPacket("clubs", "club_id", idArray.getJSONObject(i).getString("club_id"));

                if (!packet.get("club_name").equals("Dashboard") && !packet.get("club_name").equals("Directory")) {
                    packet.addMetadata("button");
                    dataPackets.add(packet);
                }
            }

            return dataPackets;
        }

        // Role-based packet retrieval
        switch (bundlePacket.getString("role")) {
            case "admin":
                // Future: Add admin-specific packets
            case "advisor":
                // Future: Add advisor-specific packets
            case "leadership":
                // Future: Add leadership-specific packets
            case "member":
                // For now, all roles receive the same base data

                // Add the club info packet
                dataPackets.add(clubPacket);

                // Add the event info packet for the club
                DataPacket eventPacket = new DataPacket("events", "sponsor_club", clubPacket.getString("club_id"));
                dataPackets.add(eventPacket);

                break;
            default:
                throw new IllegalArgumentException("Invalid role: " + bundlePacket.getString("role"));
        }

        return dataPackets;
    }

    /**
     * Helper method to fetch a single packet based on this bundle's ID and a specified table.
     *
     * @param table the Supabase table to query
     * @return the fetched {@link DataPacket}
     */
    private DataPacket fetchPacketByBundle(String table) {
        return new DataPacket(table, "bundle_id", bundlePacket.getString("bundle_id"));
    }
}
