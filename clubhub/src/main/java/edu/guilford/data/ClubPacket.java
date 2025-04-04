package edu.guilford.data;

import org.json.JSONObject;

public class ClubPacket {

    // Club attributes
    private String club_name;
    private int founding_year;
    private String club_description;
    private String club_website;
    private String meeting_schedule;
    private String meeting_location;
    private String club_email;
    private boolean active;

    // Full Constructor (Use for SignUp operation only)
    public ClubPacket(String club_name,
            int founding_year,
            String club_description,
            String club_website,
            String meeting_schedule,
            String meeting_location,
            String club_email,
            boolean active) {
        this.club_name = club_name;
        this.founding_year = founding_year;
        this.club_description = club_description;
        this.club_website = club_website;
        this.meeting_schedule = meeting_schedule;
        this.meeting_location = meeting_location;
        this.club_email = club_email;
        this.active = active;
    }

    // Minimum Constructor (Use for Login operation)
    public ClubPacket(String club_name) {
        this.club_name = club_name;
    }

    // Get JSONObject representing ClubPacket with non-null components only
    public JSONObject getJSON() {
        JSONObject json = new JSONObject();
        if (club_name != null) {
            json.put("club_name", club_name);
        }
        if (founding_year != 0) {
            json.put("founding_year", founding_year);
        }
        if (club_description != null) {
            json.put("club_description", club_description);
        }
        if (club_website != null) {
            json.put("club_website", club_website);
        }
        if (meeting_schedule != null) {
            json.put("meeting_schedule", meeting_schedule);
        }
        if (meeting_location != null) {
            json.put("meeting_location", meeting_location);
        }
        if (club_email != null) {
            json.put("club_email", club_email);
        }
        json.put("active", active);
        return json;
    }

    @Override
    public String toString() {
        return "ClubPacket{"
                + "club_name='" + club_name + '\''
                + ", founding_year=" + founding_year
                + ", club_description='" + club_description + '\''
                + ", club_website='" + club_website + '\''
                + ", meeting_schedule='" + meeting_schedule + '\''
                + ", meeting_location='" + meeting_location + '\''
                + ", club_email='" + club_email + '\''
                + ", active=" + active
                + '}';
    }

    /**
     * @return true if all fields are defined, false otherwise
     */
    public boolean isComplete() {
        return club_name != null
                && founding_year > 0
                && club_description != null
                && club_website != null
                && meeting_schedule != null
                && meeting_location != null
                && club_email != null
                && active;
    }

    // Getters and Setters

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public int getFounding_year() {
        return founding_year;
    }

    public void setFounding_year(int founding_year) {
        this.founding_year = founding_year;
    }

    public String getClub_description() {
        return club_description;
    }

    public void setClub_description(String club_description) {
        this.club_description = club_description;
    }

    public String getClub_website() {
        return club_website;
    }

    public void setClub_website(String club_website) {
        this.club_website = club_website;
    }

    public String getMeeting_schedule() {
        return meeting_schedule;
    }

    public void setMeeting_schedule(String meeting_schedule) {
        this.meeting_schedule = meeting_schedule;
    }

    public String getMeeting_location() {
        return meeting_location;
    }

    public void setMeeting_location(String meeting_location) {
        this.meeting_location = meeting_location;
    }

    public String getClub_email() {
        return club_email;
    }

    public void setClub_email(String club_email) {
        this.club_email = club_email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
