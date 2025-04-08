package edu.guilford.data;

import java.util.UUID;

import edu.guilford.data.packets.DataPacket;

/**
 * A ContentBundle contains a list of Data objects
 * that each correspond to a content panel. 
 */
public class DataBundle {

    /**
     * Each bundle in SB contains a role field (String) that indicates the user's permission level in the club
     */
    private enum UserRole {
        MEMBER,     // --> "member"
        LEADERSHIP, // --> "leadership"
        ADVISOR,    // --> "advisor"
        ADMIN       // --> "admin"
    }

    // Data attributes
    private UserRole userRole;  // The role of the user in the club
    private UUID bundle_id;     // The UUID of the bundle in SB

    public DataBundle(UUID bundle_id) {
        this.bundle_id = bundle_id;
        DataPacket bundlePacket = new DataPacket("bundles", "bundle_id", bundle_id);

        System.out.println("Bundle Packet: " + bundlePacket.toString());
    }


}
