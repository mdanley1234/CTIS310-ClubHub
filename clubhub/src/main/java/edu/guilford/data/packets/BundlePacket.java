package edu.guilford.data.packets;

import java.util.UUID;

@Deprecated
public class BundlePacket extends DataPacket {

    // Signup Constructor
    public BundlePacket(UUID profile_id,
            UUID club_id,
            String role) {
        this.put("profile_id", profile_id);
        this.put("club_id", club_id);
        this.put("role", role);
    }

    // bundlePacket SB constructor
    public BundlePacket(UUID id) {
        super("bundles", "bundle_id", id);
    }

}
