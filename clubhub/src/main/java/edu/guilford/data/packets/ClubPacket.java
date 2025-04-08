package edu.guilford.data.packets;

import java.util.UUID;

public class ClubPacket extends DataPacket {

    // Signup Constructor
    public ClubPacket(String club_name,
            int founding_year,
            String club_description,
            String club_website,
            String meeting_schedule,
            String meeting_location,
            String club_email,
            boolean active) {
        this.put("club_name", club_name);
        this.put("founding_year", founding_year);
        this.put("club_description", club_description);
        this.put("club_website", club_website);
        this.put("meeting_schedule", meeting_schedule);
        this.put("meeting_location", meeting_location);
        this.put("club_email", club_email);
        this.put("active", active);
    }

    // clubPacket SB constructor
    public ClubPacket(UUID id) {
        super("clubs", "club_id", id);
    }

}
